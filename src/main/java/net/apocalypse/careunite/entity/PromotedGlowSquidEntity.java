
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
import net.minecraftforge.common.ForgeMod;

import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.SquidSpawnProcedure;
import net.apocalypse.careunite.procedures.GlowSquidSkillProcedure;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.CaerulaArborMod;

import javax.annotation.Nullable;

import java.util.EnumSet;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.BreedGoal;

public class PromotedGlowSquidEntity extends Animal implements RangedAttackMob, GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(PromotedGlowSquidEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(PromotedGlowSquidEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(PromotedGlowSquidEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_SKILL_P = SynchedEntityData.defineId(PromotedGlowSquidEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_TRANS_T = SynchedEntityData.defineId(PromotedGlowSquidEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_DURATION = SynchedEntityData.defineId(PromotedGlowSquidEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	protected final WaterBoundPathNavigation waterNavigation;
   	protected final GroundPathNavigation groundNavigation;

   	private final MoveControl landControl;
   	private final ApostleProkaryoteEntity.SeabornSwimControl swimControl;

	public PromotedGlowSquidEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.PROMOTED_GLOW_SQUID.get(), world);
	}

	public PromotedGlowSquidEntity(EntityType<PromotedGlowSquidEntity> type, Level world) {
		super(type, world);
		xpReward = 3;
		setNoAi(false);
		setMaxUpStep(0.6f);
		setPersistenceRequired();
		this.setPathfindingMalus(BlockPathTypes.WATER, 0);
		this.landControl = new MoveControl(this);
		this.swimControl = new ApostleProkaryoteEntity.SeabornSwimControl(this);
		this.moveControl = this.landControl;
		this.waterNavigation = new WaterBoundPathNavigation(this, world);
      	this.groundNavigation = new GroundPathNavigation(this, world);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "squid_glow");
		this.entityData.define(DATA_SKILL_P, 200);
		this.entityData.define(DATA_TRANS_T, 0);
		this.entityData.define(DATA_DURATION, 0);
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

	@Override
	protected PathNavigation createNavigation(Level world) {
		return new WaterBoundPathNavigation(this, world);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.25));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.25, PromotedSquidEntity.class));
		this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 1, 40));
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(1, new PromotedGlowSquidEntity.RangedAttackGoal(this, 1, 50, 12f) {
			@Override
			public boolean canUse(){
				return super.canUse() && !PromotedGlowSquidEntity.this.isBaby();
			}
			@Override
			public boolean canContinueToUse() {
				return this.canUse();
			}
		});
		this.goalSelector.addGoal(1, new PromotedGlowSquidEntity.RangedAttackGoal(this, 1, 30, 10f) {
			@Override
			public boolean canUse(){
				return super.canUse() && PromotedGlowSquidEntity.this.isBaby();
			}
			@Override
			public boolean canContinueToUse() {
				return this.canUse();
			}
		});

	}

	public class RangedAttackGoal extends Goal {
		private final PromotedGlowSquidEntity mob;
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

		public RangedAttackGoal(PromotedGlowSquidEntity p_25768_, double p_25769_, int p_25770_, float p_25771_) {
			this(p_25768_, p_25769_, p_25770_, p_25770_, p_25771_);
		}

		public RangedAttackGoal(PromotedGlowSquidEntity p_25773_, double p_25774_, int p_25775_, int p_25776_, float p_25777_) {
			if (!(p_25773_ instanceof LivingEntity)) {
				throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
			} else {
				this.rangedAttackMob = p_25773_;
				this.mob = p_25773_;
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
				return this.mob.getEntityData().get(DATA_DURATION) <= 0;
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
			((PromotedGlowSquidEntity) rangedAttackMob).entityData.set(SHOOT, false);
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
					((PromotedGlowSquidEntity) rangedAttackMob).entityData.set(SHOOT, false);
					return;
				}
				((PromotedGlowSquidEntity) rangedAttackMob).entityData.set(SHOOT, true);
				float f = (float) Math.sqrt(d0) / this.attackRadius;
				float f1 = Mth.clamp(f, 0.1F, 1.0F);
				this.rangedAttackMob.performRangedAttack(this.target, f1);
				this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
			} else if (this.attackTime < 0) {
				this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) this.attackRadius, (double) this.attackIntervalMin, (double) this.attackIntervalMax));
			} else
				((PromotedGlowSquidEntity) rangedAttackMob).entityData.set(SHOOT, false);
		}
	}

	@Override
	public MobType getMobType() {
		return MobType.WATER;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
		super.dropCustomDeathLoot(source, looting, recentlyHitIn);
		this.spawnAtLocation(new ItemStack(Items.GLOW_INK_SAC));
	}

	@Override
	public SoundEvent getAmbientSound() {
		return SoundEvents.GLOW_SQUID_AMBIENT;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return SoundEvents.GLOW_SQUID_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.GLOW_SQUID_DEATH;
	}

	public boolean canMate(Animal pOtherAnimal) {
      if (pOtherAnimal == this) {
         return false;
      } else if (!(pOtherAnimal instanceof PromotedSquidEntity || pOtherAnimal instanceof PromotedGlowSquidEntity)) {
         return false;
      } else {
         return this.isInLove() && pOtherAnimal.isInLove();
      }
   }

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.DROWN))
			return false;
		if (this.entityData.get(DATA_TRANS_T) > 0) amount *= 0.01f;
		return super.hurt(source, amount);
	}

	@Override
	public void setHealth(float pHealth){
		if (pHealth <= 0 && !this.isBaby()){
			super.setHealth(this.getMaxHealth() * 0.75f);
			this.setAge(-1 * Mth.nextInt(this.getRandom(), 300, 1200));
			this.entityData.set(DATA_TRANS_T, 30);
			this.entityData.set(DATA_DURATION, 30);
			this.setAnimation("to_child");
			return;
		}
		super.setHealth(pHealth);
	}
	
	@Override
	protected void ageBoundaryReached() {
		super.ageBoundaryReached();
		if (this.isBaby()){
			this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0);
		} else {
			this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5);
			this.entityData.set(DATA_TRANS_T, 30);
			this.entityData.set(DATA_DURATION, 30);
			this.setAnimation("to_adult");
		}
   }

	@Override
	public void die(DamageSource pDamageSource){
		if (!this.isBaby()){
			this.setAge(-1 * Mth.nextInt(this.getRandom(), 300, 1200));
			this.entityData.set(DATA_TRANS_T, 30);
			this.entityData.set(DATA_DURATION, 30);
			this.setAnimation("to_child");
			return;
		}
		super.die(pDamageSource);
	}


	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataSKILL_P", this.entityData.get(DATA_SKILL_P));
		compound.putInt("DataTRANS_T", this.entityData.get(DATA_TRANS_T));
		compound.putInt("DataDURATION", this.entityData.get(DATA_DURATION));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataSKILL_P"))
			this.entityData.set(DATA_SKILL_P, compound.getInt("DataSKILL_P"));
		if (compound.contains("DataTRANS_T"))
			this.entityData.set(DATA_TRANS_T, compound.getInt("DataTRANS_T"));
		if (compound.contains("DataDURATION"))
			this.entityData.set(DATA_DURATION, compound.getInt("DataDURATION"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		GlowSquidSkillProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
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
	public void performRangedAttack(LivingEntity target, float flval) {
		if (this.isBaby()){
			CaerulaArborMod.queueServerWork(12, ()->{
				FishSplashEntity.shoot(this, target, true);
				}
			);
		} else {
			CaerulaArborMod.queueServerWork(6, ()->{
				FishSplashEntity.shoot(this, target, false);
				}
			);
		}

	}

	public void setBaby(boolean pBaby) {
      this.setAge(pBaby ? -1 * Mth.nextInt(this.getRandom(), 2400, 7200) : 0);
   }

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
		EntityType<?> type = CaerulaArborModEntities.PROMOTED_GLOW_SQUID.get();
		if (ageable instanceof PromotedSquidEntity && serverWorld.getRandom().nextFloat() < 0.25f) type = CaerulaArborModEntities.PROMOTED_SQUID.get();
		else if (!(ageable instanceof PromotedSquidEntity) && serverWorld.getRandom().nextFloat() < 0.0625f) type = CaerulaArborModEntities.PROMOTED_SQUID.get();
		AgeableMob retval = (AgeableMob) type.create(serverWorld);
		retval.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(retval.blockPosition()), MobSpawnType.BREEDING, null, null);
		return retval;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return stack.is(CaerulaArborModItems.OCEAN_CELL.get());
	}

	protected boolean shouldDropLoot() {
      return true;
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

	@Override
	public void aiStep() {
		super.aiStep();
		this.updateSwingTime();
		double y = this.getDeltaMovement().y + 0.15;
		double x = this.getDeltaMovement().x, z = this.getDeltaMovement().z;
		double h = Mth.sqrt((float)(x * x + z * z));
		this.upPitchO = this.upPitch;
		if (h < 0.01) this.upPitch = 0;
		else this.upPitch = -1 * (float) Mth.atan2(y, h);
	}


	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.14);
		builder = builder.add(Attributes.MAX_HEALTH, 18);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 4);
		builder = builder.add(Attributes.FOLLOW_RANGE, 20);
		builder = builder.add(ForgeMod.SWIM_SPEED.get(), 0.14);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F))) {
				if (this.isInWaterOrBubble()) {
					return event.setAndContinue(RawAnimation.begin().thenLoop("swim"));
				}
				return event.setAndContinue(RawAnimation.begin().thenLoop("land"));
			}
			if (this.isInWaterOrBubble()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle_land"));
		}
		return PlayState.STOP;
	}


	String prevAnim = "empty";

	private PlayState attackingPredicate(AnimationState event) {
		double d1 = this.getX() - this.xOld;
		double d0 = this.getZ() - this.zOld;
		float velocity = (float) Math.sqrt(d1 * d1 + d0 * d0);
		if (getAttackAnim(event.getPartialTick()) > 0f && !this.swinging) {
			this.swinging = true;
			this.lastSwing = level().getGameTime();
		}
		if (this.swinging && this.lastSwing + 15L <= level().getGameTime()) {
			this.swinging = false;
		}
		if ((this.swinging || this.entityData.get(SHOOT)) && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			if (this.isBaby()) return event.setAndContinue(RawAnimation.begin().thenPlay("attack_child"));
			return event.setAndContinue(RawAnimation.begin().thenPlay("attack"));
		}
		return PlayState.CONTINUE;
	}


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

	public float getScale() {
      return this.isBaby() ? 0.75F : 1.0F;
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

	public boolean shouldShowBig(){
		return !this.isBaby() || this.entityData.get(DATA_TRANS_T) > 0;
	}
	public boolean shouldShowSmall(){
		return this.isBaby() || this.entityData.get(DATA_TRANS_T) > 0;
	}

	float upPitch = 0;
	float upPitchO = 0;
	
	public float getUpPitch(float partialTick){
		return Mth.lerp(partialTick, upPitchO, upPitch);
	}

}
