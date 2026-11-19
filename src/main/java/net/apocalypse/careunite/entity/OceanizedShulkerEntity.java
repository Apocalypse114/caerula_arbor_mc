
package net.apocalypse.careunite.entity;

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

import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.ShulkerWalkingProcedure;
import net.apocalypse.careunite.procedures.ShulkerTicksProcedure;
import net.apocalypse.careunite.procedures.ShulkerStartWalkProcedure;
import net.apocalypse.careunite.procedures.ShulkerSelectVariantProcedure;
import net.apocalypse.careunite.procedures.MobAliveProcedure;
import net.apocalypse.careunite.procedures.ConstructShulkerProcedure;
import net.apocalypse.careunite.procedures.ShulkerDropChitinProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

public class OceanizedShulkerEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(OceanizedShulkerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(OceanizedShulkerEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(OceanizedShulkerEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_SHOOT_DELAY = SynchedEntityData.defineId(OceanizedShulkerEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<String> DATA_DIRECTION = SynchedEntityData.defineId(OceanizedShulkerEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Boolean> DATA_WALKING = SynchedEntityData.defineId(OceanizedShulkerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_PEEK_TIME = SynchedEntityData.defineId(OceanizedShulkerEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(OceanizedShulkerEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public OceanizedShulkerEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.OCEANIZED_SHULKER.get(), world);
	}

	public OceanizedShulkerEntity(EntityType<OceanizedShulkerEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
		setMaxUpStep(1f);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "oceanized_shulker");
		this.entityData.define(DATA_SHOOT_DELAY, 0);
		this.entityData.define(DATA_DIRECTION, "up");
		this.entityData.define(DATA_WALKING, false);
		this.entityData.define(DATA_PEEK_TIME, 0);
		this.entityData.define(DATA_VARIANT, 0);
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public String getTexture() {
		return this.entityData.get(TEXTURE);
	}

	@Override
	public boolean canCollideWith(Entity entity) {
		return true;
	}

	@Override
	public boolean canBeCollidedWith() {
		Entity entity = this;
		Level world = entity.level();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		return MobAliveProcedure.execute(entity);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 4;
			}

			@Override
			public boolean canUse() {
				double x = OceanizedShulkerEntity.this.getX();
				double y = OceanizedShulkerEntity.this.getY();
				double z = OceanizedShulkerEntity.this.getZ();
				Entity entity = OceanizedShulkerEntity.this;
				Level world = OceanizedShulkerEntity.this.level();
				return super.canUse() && ShulkerWalkingProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedShulkerEntity.this.getX();
				double y = OceanizedShulkerEntity.this.getY();
				double z = OceanizedShulkerEntity.this.getZ();
				Entity entity = OceanizedShulkerEntity.this;
				Level world = OceanizedShulkerEntity.this.level();
				return super.canContinueToUse() && ShulkerWalkingProcedure.execute(entity);
			}

		});
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = OceanizedShulkerEntity.this.getX();
				double y = OceanizedShulkerEntity.this.getY();
				double z = OceanizedShulkerEntity.this.getZ();
				Entity entity = OceanizedShulkerEntity.this;
				Level world = OceanizedShulkerEntity.this.level();
				return super.canUse() && ShulkerWalkingProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedShulkerEntity.this.getX();
				double y = OceanizedShulkerEntity.this.getY();
				double z = OceanizedShulkerEntity.this.getZ();
				Entity entity = OceanizedShulkerEntity.this;
				Level world = OceanizedShulkerEntity.this.level();
				return super.canContinueToUse() && ShulkerWalkingProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = OceanizedShulkerEntity.this.getX();
				double y = OceanizedShulkerEntity.this.getY();
				double z = OceanizedShulkerEntity.this.getZ();
				Entity entity = OceanizedShulkerEntity.this;
				Level world = OceanizedShulkerEntity.this.level();
				return super.canUse() && ShulkerWalkingProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedShulkerEntity.this.getX();
				double y = OceanizedShulkerEntity.this.getY();
				double z = OceanizedShulkerEntity.this.getZ();
				Entity entity = OceanizedShulkerEntity.this;
				Level world = OceanizedShulkerEntity.this.level();
				return super.canContinueToUse() && ShulkerWalkingProcedure.execute(entity);
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
	public SoundEvent getAmbientSound() {
		return SoundEvents.SHULKER_AMBIENT;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return peekTime() > 0 ? SoundEvents.SHULKER_HURT : SoundEvents.SHULKER_HURT_CLOSED;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.SHULKER_DEATH;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (peekTime() <= 0 && source.getDirectEntity() instanceof AbstractArrow)
			return false;
		if (source.is(DamageTypes.FALL))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		return super.hurt(source, amount);
	}

	@Override
	public void setHealth(float pHealth){
		if (pHealth <= 0 && ShulkerStartWalkProcedure.execute(this)) return;
		if (isBedrock() && peekTime() <= 0 && pHealth < this.getHealth()) return;
        super.setHealth(pHealth);
	}

	@Override
	public void die(DamageSource source) {
		if (!ShulkerStartWalkProcedure.execute(this)) super.die(source);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		ShulkerSelectVariantProcedure.execute(this);
		return retval;
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand){
		InteractionResult sup = super.mobInteract(player, hand);
		InteractionResult ths = ConstructShulkerProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this, player);
		if (ths == InteractionResult.PASS) return sup;
		return ths;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataSHOOT_DELAY", this.entityData.get(DATA_SHOOT_DELAY));
		compound.putString("DataDIRECTION", this.entityData.get(DATA_DIRECTION));
		compound.putBoolean("DataWALKING", this.entityData.get(DATA_WALKING));
		compound.putInt("DataPEEK_TIME", this.entityData.get(DATA_PEEK_TIME));
		compound.putInt("DataVARIANT", this.entityData.get(DATA_VARIANT));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataSHOOT_DELAY"))
			this.entityData.set(DATA_SHOOT_DELAY, compound.getInt("DataSHOOT_DELAY"));
		if (compound.contains("DataDIRECTION"))
			this.entityData.set(DATA_DIRECTION, compound.getString("DataDIRECTION"));
		if (compound.contains("DataWALKING"))
			this.entityData.set(DATA_WALKING, compound.getBoolean("DataWALKING"));
		if (compound.contains("DataPEEK_TIME"))
			this.entityData.set(DATA_PEEK_TIME, compound.getInt("DataPEEK_TIME"));
		if (compound.contains("DataVARIANT"))
			this.entityData.set(DATA_VARIANT, compound.getInt("DataVARIANT"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		ShulkerTicksProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 55);
		builder = builder.add(Attributes.ARMOR, 5);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.oceanized_shulker.die"));
		}
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.1F && event.getLimbSwingAmount() < 0.1F))
) {
				if (this.isWalking())
					return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_shulker.move"));
				if (peekTime() <= 0) event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_shulker.idle"));
				event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_shulker.idle_peeking"));
			}
			if (this.isWalking())
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_shulker.idel_withfeet"));
			else{
				if (this.peekTime() > 0) return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_shulker.idle_peeking"));
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_shulker.idle"));
			}
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
		if (this.deathTime >= 20) {
			this.remove(OceanizedShulkerEntity.RemovalReason.KILLED);
			ShulkerDropChitinProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
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
		data.add(new AnimationController<>(this, "movement", 2, this::movementPredicate));
		data.add(new AnimationController<>(this, "procedure", 2, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	public boolean isWalking(){
		return this.entityData.get(DATA_WALKING);
	}

	private int peekTime(){
		return this.entityData.get(DATA_PEEK_TIME);
	}

	public Direction getAttachDirection(){
		return Direction.byName(this.entityData.get(DATA_DIRECTION));
	}

	private boolean isBedrock(){
		return this.entityData.get(DATA_VARIANT) == 3;
	}
}
