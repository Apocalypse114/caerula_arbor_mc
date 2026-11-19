
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

import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.IsCarmenDurativeProcedure;
import net.apocalypse.careunite.procedures.CarmenSkillsProcedure;
import net.apocalypse.careunite.procedures.CarmenDoHurtTargetProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import java.util.List;

public class SaintCarmenEntity extends Animal implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(SaintCarmenEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(SaintCarmenEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(SaintCarmenEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_skillP1 = SynchedEntityData.defineId(SaintCarmenEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_skillP2 = SynchedEntityData.defineId(SaintCarmenEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_shootP = SynchedEntityData.defineId(SaintCarmenEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_bullet = SynchedEntityData.defineId(SaintCarmenEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_duration = SynchedEntityData.defineId(SaintCarmenEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_reloadP = SynchedEntityData.defineId(SaintCarmenEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public SaintCarmenEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.SAINT_CARMEN.get(), world);
	}

	public SaintCarmenEntity(EntityType<SaintCarmenEntity> type, Level world) {
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
		this.entityData.define(TEXTURE, "saint_carmen");
		this.entityData.define(DATA_skillP1, 200);
		this.entityData.define(DATA_skillP2, 100);
		this.entityData.define(DATA_shootP, 80);
		this.entityData.define(DATA_bullet, 3);
		this.entityData.define(DATA_duration, 0);
		this.entityData.define(DATA_reloadP, 500);
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public String getTexture() {
		return this.entityData.get(TEXTURE);
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 1.7F;
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.3, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 6.25;
			}

			@Override
			public boolean canUse() {
				double x = SaintCarmenEntity.this.getX();
				double y = SaintCarmenEntity.this.getY();
				double z = SaintCarmenEntity.this.getZ();
				Entity entity = SaintCarmenEntity.this;
				Level world = SaintCarmenEntity.this.level();
				return super.canUse() && IsCarmenDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = SaintCarmenEntity.this.getX();
				double y = SaintCarmenEntity.this.getY();
				double z = SaintCarmenEntity.this.getZ();
				Entity entity = SaintCarmenEntity.this;
				Level world = SaintCarmenEntity.this.level();
				return super.canContinueToUse() && IsCarmenDurativeProcedure.execute(entity);
			}

		});
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Monster.class, true, false));
		this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = SaintCarmenEntity.this.getX();
				double y = SaintCarmenEntity.this.getY();
				double z = SaintCarmenEntity.this.getZ();
				Entity entity = SaintCarmenEntity.this;
				Level world = SaintCarmenEntity.this.level();
				return super.canUse() && IsCarmenDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = SaintCarmenEntity.this.getX();
				double y = SaintCarmenEntity.this.getY();
				double z = SaintCarmenEntity.this.getZ();
				Entity entity = SaintCarmenEntity.this;
				Level world = SaintCarmenEntity.this.level();
				return super.canContinueToUse() && IsCarmenDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = SaintCarmenEntity.this.getX();
				double y = SaintCarmenEntity.this.getY();
				double z = SaintCarmenEntity.this.getZ();
				Entity entity = SaintCarmenEntity.this;
				Level world = SaintCarmenEntity.this.level();
				return super.canUse() && IsCarmenDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = SaintCarmenEntity.this.getX();
				double y = SaintCarmenEntity.this.getY();
				double z = SaintCarmenEntity.this.getZ();
				Entity entity = SaintCarmenEntity.this;
				Level world = SaintCarmenEntity.this.level();
				return super.canContinueToUse() && IsCarmenDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(6, new FloatGoal(this));
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
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataskillP1", this.entityData.get(DATA_skillP1));
		compound.putInt("DataskillP2", this.entityData.get(DATA_skillP2));
		compound.putInt("DatashootP", this.entityData.get(DATA_shootP));
		compound.putInt("Databullet", this.entityData.get(DATA_bullet));
		compound.putInt("Dataduration", this.entityData.get(DATA_duration));
		compound.putInt("DatareloadP", this.entityData.get(DATA_reloadP));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataskillP1"))
			this.entityData.set(DATA_skillP1, compound.getInt("DataskillP1"));
		if (compound.contains("DataskillP2"))
			this.entityData.set(DATA_skillP2, compound.getInt("DataskillP2"));
		if (compound.contains("DatashootP"))
			this.entityData.set(DATA_shootP, compound.getInt("DatashootP"));
		if (compound.contains("Databullet"))
			this.entityData.set(DATA_bullet, compound.getInt("Databullet"));
		if (compound.contains("Dataduration"))
			this.entityData.set(DATA_duration, compound.getInt("Dataduration"));
		if (compound.contains("DatareloadP"))
			this.entityData.set(DATA_reloadP, compound.getInt("DatareloadP"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		CarmenSkillsProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public boolean doHurtTarget(Entity pEntity){
		CarmenDoHurtTargetProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), pEntity, this);
		return true;
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
		SaintCarmenEntity retval = CaerulaArborModEntities.SAINT_CARMEN.get().create(serverWorld);
		retval.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(retval.blockPosition()), MobSpawnType.BREEDING, null, null);
		return retval;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return List.of().contains(stack.getItem());
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.16);
		builder = builder.add(Attributes.MAX_HEALTH, 280);
		builder = builder.add(Attributes.ARMOR, 8);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 22);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))

					&& !this.isAggressive()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.saint_carmen.move"));
			}
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.saint_carmen.die"));
			}
			if (this.isAggressive() && event.isMoving()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.saint_carmen.sprint"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.saint_carmen.idle"));
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
		if (this.swinging && this.lastSwing + 16L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.saint_carmen.melee"));
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
		if (this.deathTime == 20) {
			this.remove(SaintCarmenEntity.RemovalReason.KILLED);
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
		data.add(new AnimationController<>(this, "movement", 0, this::movementPredicate));
		data.add(new AnimationController<>(this, "attacking", 0, this::attackingPredicate));
		data.add(new AnimationController<>(this, "procedure", 0, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
