
package net.apocalypse.careunite.entity;

import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.GeoEntity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.MonstrositySkillsProcedure;
import net.apocalypse.careunite.procedures.MonstrosityDoHurtTargetProcedure;
import net.apocalypse.careunite.procedures.InitMonstrosityProcedure;
import net.apocalypse.careunite.procedures.HealMonstrosityProcedure;
import net.apocalypse.careunite.procedures.IsMonstrosityDurativeProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import javax.annotation.Nullable;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.tags.DamageTypeTags;

public class IberiaMonstrosityEntity extends AbstractGolem implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(IberiaMonstrosityEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(IberiaMonstrosityEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(IberiaMonstrosityEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_DURATION = SynchedEntityData.defineId(IberiaMonstrosityEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_MELEE_SKILL_P = SynchedEntityData.defineId(IberiaMonstrosityEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_RANGED_SKILL_P = SynchedEntityData.defineId(IberiaMonstrosityEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_SHOOT_P = SynchedEntityData.defineId(IberiaMonstrosityEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_MELEE_SKILL_TICK = SynchedEntityData.defineId(IberiaMonstrosityEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_RANGED_SKILL_TICK = SynchedEntityData.defineId(IberiaMonstrosityEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public static final EntityDataAccessor<Float> GUN_X = SynchedEntityData.defineId(IberiaMonstrosityEntity.class, EntityDataSerializers.FLOAT);
	public static final EntityDataAccessor<Float> GUN_Y = SynchedEntityData.defineId(IberiaMonstrosityEntity.class, EntityDataSerializers.FLOAT);
	public static final EntityDataAccessor<Float> GUN_Z = SynchedEntityData.defineId(IberiaMonstrosityEntity.class, EntityDataSerializers.FLOAT);

	public IberiaMonstrosityEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.IBERIA_MONSTROSITY.get(), world);
	}

	public IberiaMonstrosityEntity(EntityType<IberiaMonstrosityEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
		setMaxUpStep(1.5f);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "iberia_monstrosity");
		this.entityData.define(DATA_DURATION, 0);
		this.entityData.define(DATA_MELEE_SKILL_P, 200);
		this.entityData.define(DATA_RANGED_SKILL_P, 300);
		this.entityData.define(DATA_SHOOT_P, 80);
		this.entityData.define(DATA_MELEE_SKILL_TICK, 0);
		this.entityData.define(DATA_RANGED_SKILL_TICK, 0);
		this.entityData.define(GUN_X, 0F);
		this.entityData.define(GUN_Y, 0F);
		this.entityData.define(GUN_Z, 0F);
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public String getTexture() {
		return this.entityData.get(TEXTURE);
	}

	public void setGunPos(float x, float y, float z){
		this.entityData.set(GUN_X, x);
		this.entityData.set(GUN_Y, y);
		this.entityData.set(GUN_Z, z);
	}

	public Vec3 getGunPos(){
		return new Vec3(
			(double)this.entityData.get(GUN_X), 
			(double)this.entityData.get(GUN_Y), 
			(double)this.entityData.get(GUN_Z));
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 2, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 16;
			}

			@Override
			public boolean canUse() {
				IberiaMonstrosityEntity entity = IberiaMonstrosityEntity.this;
				return super.canUse() && entity.shouldAttack();
			}

			@Override
			public boolean canContinueToUse() {
				IberiaMonstrosityEntity entity = IberiaMonstrosityEntity.this;
				return super.canContinueToUse() && entity.shouldAttack();
			}

		});
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Monster.class, true, false));
		this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				IberiaMonstrosityEntity entity = IberiaMonstrosityEntity.this;
				return super.canUse() && entity.shouldAttack();
			}

			@Override
			public boolean canContinueToUse() {
				IberiaMonstrosityEntity entity = IberiaMonstrosityEntity.this;
				return super.canContinueToUse() && entity.shouldAttack();
			}
		});
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				IberiaMonstrosityEntity entity = IberiaMonstrosityEntity.this;
				return super.canUse() && entity.shouldAttack();
			}

			@Override
			public boolean canContinueToUse() {
				IberiaMonstrosityEntity entity = IberiaMonstrosityEntity.this;
				return super.canContinueToUse() && entity.shouldAttack();
			}
		});
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public double getPassengersRidingOffset() {
		return super.getPassengersRidingOffset() + 1;
	}

	public SoundEvent HURT = null;
	public SoundEvent DIE = null;

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		if (HURT == null) HURT = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mons_hit"));
		return HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		if (DIE == null) DIE = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mons_die"));
		return DIE;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.FALL))
			return false;
		if (source.is(DamageTypes.CACTUS))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		if (source.is(DamageTypes.EXPLOSION))
			return false;
		if (source.is(DamageTypes.FALLING_ANVIL))
			return false;
		if (source.is(DamageTypeTags.IS_FIRE)) return false;
		if (this.withinMeleeSkill() || this.withinRangedSkill()) amount *= 0.25f;
		return super.hurt(source, amount);
	}

	@Override
	public boolean doHurtTarget(Entity pEntity){
		MonstrosityDoHurtTargetProcedure.execute(this.level(), pEntity, this);
		return true;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		InitMonstrosityProcedure.execute(this);
		this.setGunPos((float)this.getX(), (float)(this.getY()+2.375), (float)this.getZ());
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataDURATION", this.entityData.get(DATA_DURATION));
		compound.putInt("DataMELEE_SKILL_P", this.entityData.get(DATA_MELEE_SKILL_P));
		compound.putInt("DataRANGED_SKILL_P", this.entityData.get(DATA_RANGED_SKILL_P));
		compound.putInt("DataSHOOT_P", this.entityData.get(DATA_SHOOT_P));
		compound.putInt("DataMELEE_SKILL_TICK", this.entityData.get(DATA_MELEE_SKILL_TICK));
		compound.putInt("DataRANGED_SKILL_TICK", this.entityData.get(DATA_RANGED_SKILL_TICK));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataDURATION"))
			this.entityData.set(DATA_DURATION, compound.getInt("DataDURATION"));
		if (compound.contains("DataMELEE_SKILL_P"))
			this.entityData.set(DATA_MELEE_SKILL_P, compound.getInt("DataMELEE_SKILL_P"));
		if (compound.contains("DataRANGED_SKILL_P"))
			this.entityData.set(DATA_RANGED_SKILL_P, compound.getInt("DataRANGED_SKILL_P"));
		if (compound.contains("DataSHOOT_P"))
			this.entityData.set(DATA_SHOOT_P, compound.getInt("DataSHOOT_P"));
		if (compound.contains("DataMELEE_SKILL_TICK"))
			this.entityData.set(DATA_MELEE_SKILL_TICK, compound.getInt("DataMELEE_SKILL_TICK"));
		if (compound.contains("DataRANGED_SKILL_TICK"))
			this.entityData.set(DATA_RANGED_SKILL_TICK, compound.getInt("DataRANGED_SKILL_TICK"));
	}

	@Override
	public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();
		Entity entity = this;
		Level world = this.level();

		InteractionResult res = HealMonstrosityProcedure.execute(world, x, y, z, entity, sourceentity);

		if (sourceentity.getItemInHand(hand).isEmpty()){
			sourceentity.startRiding(this);
			return InteractionResult.sidedSuccess(world.isClientSide());
		}
		super.mobInteract(sourceentity, hand);
		return res;
	}

	@Override
	public void baseTick() {
		super.baseTick();
		MonstrositySkillsProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public void travel(Vec3 dir) {
		Entity entity = this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0);
		if (this.isVehicle()) {
			this.setYRot(entity.getYRot());
			this.yRotO = this.getYRot();
			this.setXRot(entity.getXRot() * 0.5F);
			this.setRot(this.getYRot(), this.getXRot());
			this.yBodyRot = entity.getYRot();
			this.yHeadRot = entity.getYRot();
			if (entity instanceof LivingEntity passenger) {
				this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
				float forward = passenger.zza;
				float strafe = 0;
				super.travel(new Vec3(strafe, 0, forward));
			}
			double d1 = this.getX() - this.xo;
			double d0 = this.getZ() - this.zo;
			float f1 = (float) Math.sqrt(d1 * d1 + d0 * d0) * 4;
			if (f1 > 1.0F)
				f1 = 1.0F;
			this.walkAnimation.setSpeed(this.walkAnimation.speed() + (f1 - this.walkAnimation.speed()) * 0.4F);
			this.walkAnimation.position(this.walkAnimation.position() + this.walkAnimation.speed());
			this.calculateEntityAnimation(true);
			return;
		}
		super.travel(dir);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.updateSwingTime();
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 500);
		builder = builder.add(Attributes.ARMOR, 22);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 19);
		builder = builder.add(Attributes.FOLLOW_RANGE, 31);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 10);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.iberia_monstrosity.die"));
		}
		if (this.withinMeleeSkill()){
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.iberia_monstrosity.melee_skill"));
		}
		if (this.withinRangedSkill()){
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.iberia_monstrosity.shoot"));
		}

		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.05F && event.getLimbSwingAmount() < 0.05F))
&& !this.isAggressive()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.iberia_monstrosity.move"));
			}
			if (this.isAggressive() && event.isMoving()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.iberia_monstrosity.run"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.iberia_monstrosity.idle"));
		}
		return PlayState.STOP;
	}

	private PlayState attackingPredicate(AnimationState event) {
		double d1 = this.getX() - this.xOld;
		double d0 = this.getZ() - this.zOld;
		float velocity = (float) Math.sqrt(d1 * d1 + d0 * d0);
		if (getAttackAnim(event.getPartialTick()) > 0f && !this.swinging) {
			this.swinging = true;
			this.lastSwing = level().getGameTime();
		}
		if (this.swinging && this.lastSwing + 30L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			float anim = this.level().getRandom().nextFloat();
			if (anim < 0.33f){
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.iberia_monstrosity.melee"));
			} else if (anim < 0.67f){
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.iberia_monstrosity.melee_1"));
			}
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.iberia_monstrosity.melee_2"));
		}
		return PlayState.CONTINUE;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationState event) {
		if (!animationprocedure.equals("empty") && event.getController().getAnimationState() == AnimationController.State.STOPPED || (!this.animationprocedure.equals(prevAnim) && !this.animationprocedure.equals("empty"))) {
			if (!this.animationprocedure.equals(prevAnim))
				event.getController().forceAnimationReset();
			event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
			if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
				this.animationprocedure = "empty";
				event.getController().forceAnimationReset();
			}
		} else if (animationprocedure.equals("empty")) {
			prevAnim = "empty";
			return PlayState.STOP;
		}
		prevAnim = this.animationprocedure;
		return PlayState.CONTINUE;
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime >= 30) {
			this.remove(IberiaMonstrosityEntity.RemovalReason.KILLED);
			this.dropExperience();
		}
	}

	public String getSyncedAnimation() {
		return this.entityData.get(ANIMATION);
	}

	public void setAnimation(String animation) {
		this.entityData.set(ANIMATION, animation);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar data) {
		data.add(new AnimationController<>(this, "movement", 1, this::movementPredicate));
		data.add(new AnimationController<>(this, "attacking", 1, this::attackingPredicate));
		data.add(new AnimationController<>(this, "procedure", 1, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
	public boolean withinMeleeSkill(){
		return this.entityData.get(DATA_MELEE_SKILL_TICK) > 0;
	}
	public boolean withinRangedSkill(){
		return this.entityData.get(DATA_RANGED_SKILL_TICK) > 0;
	}
	public float deathProgress(float partialTick){
		if (!this.isDeadOrDying()) return 0.75f;
		return Math.max(0.75f - (this.deathTime + partialTick) * 0.025f, 0);
	}
	public boolean shouldAttack(){
		return IsMonstrosityDurativeProcedure.execute(this) && !withinMeleeSkill() && !withinRangedSkill();
	}
}
