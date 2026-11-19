
package net.apocalypse.careunite.entity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.*;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.GeoEntity;

import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.procedures.GuardianTickProcedure;
import net.apocalypse.careunite.procedures.GuardianSpawnProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import javax.annotation.Nullable;

import java.util.EnumSet;
import net.minecraftforge.common.ForgeMod;

public class PromotedGuardianEntity extends Monster implements RangedAttackMob, GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(PromotedGuardianEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(PromotedGuardianEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(PromotedGuardianEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_LOCK_TICK = SynchedEntityData.defineId(PromotedGuardianEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_TARGET_ID = SynchedEntityData.defineId(PromotedGuardianEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_EXTRA_EYE = SynchedEntityData.defineId(PromotedGuardianEntity.class, EntityDataSerializers.INT);

	public final int[] clientEyeBlinkTick = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
	public final int[] clientEyeNextBlinkTick = new int[]{0, 0, 0, 0, 0, 0, 0, 0};

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	public String animationprocedure = "empty";

	public PromotedGuardianEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.PROMOTED_GUARDIAN.get(), world);
	}

	public PromotedGuardianEntity(EntityType<PromotedGuardianEntity> type, Level world) {
		super(type, world);
		xpReward = 24;
		setNoAi(false);
		setMaxUpStep(2f);
		this.moveControl = new FlyingMoveControl(this, 10, true);
		for (int i = 0; i<8; i++){
			clientEyeNextBlinkTick[i] = Mth.nextInt(this.getRandom(), 10, 80);
		}
	}

	public Entity getClientTarget(){
		return this.level().getEntity(entityData.get(DATA_TARGET_ID));
	}
	public int getLockTick(){
		return this.entityData.get(DATA_LOCK_TICK);
	}
	public boolean shouldEyeBlink(int index){
		return clientEyeBlinkTick[index] > 0;
	}
	public int getExtraEye(){return entityData.get(DATA_EXTRA_EYE);}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "promoted_guardian");
		this.entityData.define(DATA_LOCK_TICK, 0);
		this.entityData.define(DATA_TARGET_ID, 0);
		this.entityData.define(DATA_EXTRA_EYE, 0);
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public String getTexture() {
		return this.entityData.get(TEXTURE);
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 0.5F;
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		return new FlyingPathNavigation(this, world);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, IronGolem.class, false, false));
		this.targetSelector.addGoal(3, new RunFishEntity.AttackPlayerGoal(this));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Squid.class, false, false));
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1, 20) {
			@Override
			protected Vec3 getPosition() {
				RandomSource random = PromotedGuardianEntity.this.getRandom();
				double dir_x = PromotedGuardianEntity.this.getX() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_y = PromotedGuardianEntity.this.getY() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_z = PromotedGuardianEntity.this.getZ() + ((random.nextFloat() * 2 - 1) * 16);
				return new Vec3(dir_x, dir_y, dir_z);
			}
		});
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(1, new PromotedGuardianEntity.RangedAttackGoal(this, 1.25, 100, 72f) {
			@Override
			public boolean canContinueToUse() {
				return this.canUse();
			}
		});
	}

	public class RangedAttackGoal extends Goal {
		private final Mob mob;
		private final RangedAttackMob rangedAttackMob;
		@Nullable
		private LivingEntity target;
		private int attackTime = -1;
		private final double speedModifier;
		private int seeTime;
		private final int attackIntervalMin;
		private final int attackIntervalMax;
		private final float attackRadius;
		private final float attackRadiusSqr;

		public RangedAttackGoal(RangedAttackMob p_25768_, double p_25769_, int p_25770_, float p_25771_) {
			this(p_25768_, p_25769_, p_25770_, p_25770_, p_25771_);
		}

		public RangedAttackGoal(RangedAttackMob p_25773_, double p_25774_, int p_25775_, int p_25776_, float p_25777_) {
			if (!(p_25773_ instanceof LivingEntity)) {
				throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
			} else {
				this.rangedAttackMob = p_25773_;
				this.mob = (Mob) p_25773_;
				this.speedModifier = p_25774_;
				this.attackIntervalMin = p_25775_;
				this.attackIntervalMax = p_25776_;
				this.attackRadius = p_25777_;
				this.attackRadiusSqr = p_25777_ * p_25777_;
				this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
			}
		}

		public boolean canUse() {
			LivingEntity livingentity = this.mob.getTarget();
			if (livingentity != null && livingentity.isAlive()) {
				this.target = livingentity;
				return true;
			} else {
				return false;
			}
		}

		public boolean canContinueToUse() {
			return this.canUse() || this.target.isAlive() && !this.mob.getNavigation().isDone();
		}

		public void stop() {
			this.target = null;
			this.seeTime = 0;
			this.attackTime = -1;
			((PromotedGuardianEntity) rangedAttackMob).entityData.set(SHOOT, false);
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		public void tick() {
			double d0 = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
			boolean flag = this.mob.getSensing().hasLineOfSight(this.target);
			if (flag) {
				++this.seeTime;
			} else {
				this.seeTime = 0;
			}
			if (!(d0 > (double) this.attackRadiusSqr) && this.seeTime >= 5) {
				this.mob.getNavigation().stop();
			} else {
				this.mob.getNavigation().moveTo(this.target, this.speedModifier);
			}
			this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
			if (--this.attackTime == 0) {
				if (!flag) {
					((PromotedGuardianEntity) rangedAttackMob).entityData.set(SHOOT, false);
					return;
				}
				((PromotedGuardianEntity) rangedAttackMob).entityData.set(SHOOT, true);
				float f = (float) Math.sqrt(d0) / this.attackRadius;
				float f1 = Mth.clamp(f, 0.1F, 1.0F);
				this.rangedAttackMob.performRangedAttack(this.target, f1);
				this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
			} else if (this.attackTime < 0) {
				this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) this.attackRadius, (double) this.attackIntervalMin, (double) this.attackIntervalMax));
			} else
				((PromotedGuardianEntity) rangedAttackMob).entityData.set(SHOOT, false);
		}
	}

	@Override
	public MobType getMobType() {
		return MobType.WATER;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return SoundEvents.GUARDIAN_AMBIENT;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return SoundEvents.GUARDIAN_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.GUARDIAN_DEATH;
	}

	@Override
	public boolean causeFallDamage(float l, float d, DamageSource source) {
		return false;
	}

	private int lastCounterTick = -1;
	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.DROWN))
			return false;

		boolean flag = super.hurt(source, amount);
		if (!this.isDeadOrDying() && flag && this.tickCount > lastCounterTick){
			lastCounterTick = tickCount;
			Entity sourceEnt = source.getEntity();
			if (sourceEnt == this.getTarget()) amount *= 0.8f;
			if (sourceEnt != null && this.distanceToSqr(sourceEnt) <= 6.25){
				sourceEnt.hurt(this.level().damageSources().mobAttack(this),(float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
			}
		}
		return flag;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataLOCK_TICK", this.entityData.get(DATA_LOCK_TICK));
		compound.putInt("DataTARGET_ID", this.entityData.get(DATA_TARGET_ID));
		compound.putInt("DataEXTRA_EYE", this.entityData.get(DATA_EXTRA_EYE));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataLOCK_TICK"))
			this.entityData.set(DATA_LOCK_TICK, compound.getInt("DataLOCK_TICK"));
		if (compound.contains("DataTARGET_ID"))
			this.entityData.set(DATA_TARGET_ID, compound.getInt("DataTARGET_ID"));
		if (compound.contains("DataEXTRA_EYE"))
			this.entityData.set(DATA_EXTRA_EYE, compound.getInt("DataEXTRA_EYE"));
	}

	@Override
	public void tick(){
		super.tick();
		if (this.level().isClientSide()){
			for (int i = 0; i< 8; i++){
				if (clientEyeBlinkTick[i] > 0) clientEyeBlinkTick[i] --;
				if (clientEyeNextBlinkTick[i] > 0) clientEyeNextBlinkTick[i] --;
				else {
					clientEyeNextBlinkTick[i] = Mth.nextInt(this.getRandom(), 20, 90);
					clientEyeBlinkTick[i] = 10;
				}
			}
		}
		LivingEntity target = this.getTarget();
		if (target != null && target.isAlive()){
			this.entityData.set(DATA_TARGET_ID, target.getId());
		}
	}

	@Override
	public void baseTick() {
		super.baseTick();
		GuardianTickProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public void performRangedAttack(LivingEntity target, float flval) {
		this.entityData.set(DATA_LOCK_TICK, 40);
		this.entityData.set(DATA_EXTRA_EYE, Mth.nextInt(this.getRandom(), 0, 4));
	}

	@Override
	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	@Override
	public void setNoGravity(boolean ignored) {
		if (this.isDeadOrDying()) super.setNoGravity(ignored);
		else super.setNoGravity(true);
	}

	public void aiStep() {
		super.aiStep();
		if (!this.isDeadOrDying()) this.setNoGravity(true);
	}

	public static void init() {
		SpawnPlacements.register(CaerulaArborModEntities.PROMOTED_GUARDIAN.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			return GuardianSpawnProcedure.execute(world, x, y, z);
		});
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 56);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 10);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.FLYING_SPEED, 0.3);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
		builder = builder.add(ForgeMod.SWIM_SPEED.get(), 0.5);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
			return event.setAndContinue(RawAnimation.begin().thenPlay("die"));
		}
		if (this.animationprocedure.equals("empty")) {
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}
		return PlayState.STOP;
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
		this.setNoGravity(false);
		if (this.deathTime == 30) {
			this.remove(PromotedGuardianEntity.RemovalReason.KILLED);
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
		data.add(new AnimationController<>(this, "procedure", 1, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
