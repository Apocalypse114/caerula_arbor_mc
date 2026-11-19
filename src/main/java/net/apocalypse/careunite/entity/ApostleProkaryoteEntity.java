
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
import net.minecraftforge.common.ForgeMod;

import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacements;
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
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.procedures.OceanizedPlayerProcedure;
import net.apocalypse.careunite.procedures.MarineSeabornSpawnProcedure;
import net.apocalypse.careunite.procedures.MarineDropTombProcedure;
import net.apocalypse.careunite.procedures.InitBplusMagicProcedure;
import net.apocalypse.careunite.procedures.CanAttackAnimalsProcedure;
import net.apocalypse.careunite.procedures.ApostleGetShelledProcedure;
import net.apocalypse.careunite.procedures.ApostleDoHurtTargetProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import javax.annotation.Nullable;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;

public class ApostleProkaryoteEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(ApostleProkaryoteEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(ApostleProkaryoteEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(ApostleProkaryoteEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Boolean> DATA_shelled = SynchedEntityData.defineId(ApostleProkaryoteEntity.class, EntityDataSerializers.BOOLEAN);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	protected final WaterBoundPathNavigation waterNavigation;
   	protected final GroundPathNavigation groundNavigation;

   	private final MoveControl landControl;
   	private final SeabornSwimControl swimControl;

	public ApostleProkaryoteEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.APOSTLE_PROKARYOTE.get(), world);
	}

	public ApostleProkaryoteEntity(EntityType<ApostleProkaryoteEntity> type, Level world) {
		super(type, world);
		xpReward = 8;
		setNoAi(false);
		setMaxUpStep(1.1f);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0);
		this.landControl = new MoveControl(this);
		this.swimControl = new SeabornSwimControl(this);
		this.moveControl = this.swimControl;
		this.waterNavigation = new WaterBoundPathNavigation(this, world);
      	this.groundNavigation = new GroundPathNavigation(this, world);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "apostle_unshelled");
		this.entityData.define(DATA_shelled, false);
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public String getTexture() {
		return this.entityData.get(TEXTURE);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

//	@Override
//	protected PathNavigation createNavigation(Level world) {
//		if (this.isInWater()) return new WaterBoundPathNavigation(this, world);
//		return super.createNavigation(world);
//	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 6.25;
			}
		});
		this.targetSelector.addGoal(3, new RunFishEntity.AttackPlayerGoal(this));
		this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1, 40));
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
	}

	@Override
	public MobType getMobType() {
		return MobType.WATER;
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.DOLPHIN_SWIM, 0.15f, 1);
	}

	public static final ResourceLocation HURT = new ResourceLocation("caerula_arbor_reunite:seaborn_generic_hit");
	public static final ResourceLocation DIE = new ResourceLocation("caerula_arbor_reunite:seaborn_death");
	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(HURT);
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(DIE);
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.DROWN))
			return false;
		return super.hurt(source, amount);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		InitBplusMagicProcedure.execute(this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putBoolean("Datashelled", this.entityData.get(DATA_shelled));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("Datashelled"))
			this.entityData.set(DATA_shelled, compound.getBoolean("Datashelled"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		ApostleGetShelledProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public void updateSwimming() {
      if (!this.level().isClientSide) {
         if (this.isEffectiveAi() && this.isInWater()) {
            this.navigation = this.waterNavigation;
            this.moveControl = this.swimControl;
            this.setSwimming(true);
         } else {
            this.navigation = this.groundNavigation;
            this.moveControl = this.landControl;
            this.setSwimming(false);
         }
      }
   }

	@Override
	public boolean doHurtTarget(Entity pEntity){
		ApostleDoHurtTargetProcedure.execute(this.level(), pEntity, this);
		return true;
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader world) {
		return world.isUnobstructed(this);
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	public static void init() {
		SpawnPlacements.register(CaerulaArborModEntities.APOSTLE_PROKARYOTE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			return MarineSeabornSpawnProcedure.execute(world, x, y, z);
		});
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 50);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 9);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(ForgeMod.SWIM_SPEED.get(), 0.8);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		boolean inWater = this.isInWaterOrBubble();
		if (this.isDeadOrDying()) {
			if(inWater) return event.setAndContinue(RawAnimation.begin().thenPlay("animation.apostle.die"));
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.apostle.die_lamd"));
		}
		if (this.animationprocedure.equals("empty")) {
			if (event.isMoving()
) {
				if (inWater) return event.setAndContinue(RawAnimation.begin().thenLoop("animation.apostle.move"));
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.apostle.move_land"));
			}
			if (inWater) return event.setAndContinue(RawAnimation.begin().thenLoop("animation.apostle.idle"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.apostle.idle_land"));
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
		if (this.swinging && this.lastSwing + 20L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.apostle.attack"));
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
		if (this.deathTime >= 20) {
			this.remove(ApostleProkaryoteEntity.RemovalReason.KILLED);
			this.dropExperience();
			MarineDropTombProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
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

	public static class SeabornSwimControl extends MoveControl {
		public SeabornSwimControl(Mob mob){
			super(mob);
		}
		@Override
		public void tick() {
			Mob entity = this.mob;
			if (entity.isInWater())
				entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.005, 0));
			if (this.operation == MoveControl.Operation.MOVE_TO && !entity.getNavigation().isDone()) {
				double dx = this.wantedX - entity.getX();
				double dy = this.wantedY - entity.getY();
				double dz = this.wantedZ - entity.getZ();
				float f = (float) (Mth.atan2(dz, dx) * (double) (180 / Math.PI)) - 90;
				float f1 = (float) (this.speedModifier * entity.getAttribute(ForgeMod.SWIM_SPEED.get()).getValue());
				entity.setYRot(this.rotlerp(entity.getYRot(), f, 10));
				entity.yBodyRot = entity.getYRot();
				entity.yHeadRot = entity.getYRot();
				if (entity.isInWater()) {
					entity.setSpeed((float) entity.getAttribute(ForgeMod.SWIM_SPEED.get()).getValue());
					float f2 = -(float) (Mth.atan2(dy, (float) Math.sqrt(dx * dx + dz * dz)) * (180 / Math.PI));
					f2 = Mth.clamp(Mth.wrapDegrees(f2), -85, 85);
					entity.setXRot(this.rotlerp(entity.getXRot(), f2, 5));
					float f3 = Mth.cos(entity.getXRot() * (float) (Math.PI / 180.0));
					entity.setZza(f3 * f1);
					entity.setYya((float) (f1 * dy));
					if (entity.tickCount % 10 ==0 && !entity.level().isClientSide()){
						LivingEntity target = entity.getTarget();
			            if(target != null && target.isAlive() && !target.isInWater() && target.getY() > entity.getY()){
			                Vec3 jump = entity.position().vectorTo(target.position()).normalize().scale((double)f1 * 0.35);
			                entity.push(jump.x , jump.y + 0.33, jump.z);
			            }
					}
				} else {
					super.tick();
				}
			} else {
				entity.setSpeed(0);
				entity.setYya(0);
				entity.setZza(0);
			}
		}
	}
}
