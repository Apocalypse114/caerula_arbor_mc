
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
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.control.MoveControl;
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
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.SetAxolotlTextureProcedure;
import net.apocalypse.careunite.procedures.RewardAxolotlHealProcedure;
import net.apocalypse.careunite.procedures.ReduceAxolotlHealProcedure;
import net.apocalypse.careunite.procedures.AxolotlTicksProcedure;
import net.apocalypse.careunite.procedures.AxolotlSpawnProcedure;
import net.apocalypse.careunite.procedures.AxolotlFakingDeathProcedure;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.CaerulaArborMod;

import javax.annotation.Nullable;

import java.util.List;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.InteractionResult;

public class PromotedAxolotlEntity extends Animal implements RangedAttackMob, GeoEntity, Bucketable {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(PromotedAxolotlEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(PromotedAxolotlEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(PromotedAxolotlEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_FAKE_DEATH_T = SynchedEntityData.defineId(PromotedAxolotlEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_FAKE_D_COOLDOWN = SynchedEntityData.defineId(PromotedAxolotlEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_HEAL_PER_S = SynchedEntityData.defineId(PromotedAxolotlEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<String> DATA_LAST_HELPER_UUID = SynchedEntityData.defineId(PromotedAxolotlEntity.class, EntityDataSerializers.STRING);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	private boolean fromBucket = false;

	protected final WaterBoundPathNavigation waterNavigation;
   	protected final GroundPathNavigation groundNavigation;

   	private final MoveControl landControl;
   	private final ApostleProkaryoteEntity.SeabornSwimControl swimControl;

	public PromotedAxolotlEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.PROMOTED_AXOLOTL.get(), world);
	}

	public PromotedAxolotlEntity(EntityType<PromotedAxolotlEntity> type, Level world) {
		super(type, world);
		xpReward = 2;
		setNoAi(false);
		setMaxUpStep(0.8f);
		setPersistenceRequired();
		this.setPathfindingMalus(BlockPathTypes.WATER, 0);
		this.landControl = new MoveControl(this);
		this.swimControl = new ApostleProkaryoteEntity.SeabornSwimControl(this);
		this.moveControl = this.landControl;
		this.waterNavigation = new WaterBoundPathNavigation(this, world);
      	this.groundNavigation = new GroundPathNavigation(this, world);
	}

	public boolean fromBucket(){
		return fromBucket;
	}

   	public void setFromBucket(boolean pFromBucket){
   	 	this.fromBucket = pFromBucket;
   	}

   	public void saveToBucketTag(ItemStack pStack){
   		Bucketable.saveDefaultDataToBucketTag(this, pStack);
      	CompoundTag compoundtag = pStack.getOrCreateTag();
      	compoundtag.putInt("texture", this.fromTexture());
   	}

   	public void loadFromBucketTag(CompoundTag pTag){
   		Bucketable.loadDefaultDataFromBucketTag(this, pTag);
   		if (pTag.contains("texture")) this.setTexture(textureFrom(pTag.getInt("texture")));
   	}

   	public static String textureFrom(int i){
   		if (i == 0) return "axolotl_undertides";
   		if (i == 1) return "axolotl_nethersea";
   		return "axolotl_cellular";
   	}
   	public int fromTexture(){
   		if (this.getTexture().equals("axolotl_undertides")) return 0;
   		if (this.getTexture().equals("axolotl_nethersea")) return 1;
   		return 2;
   	}
   	public ItemStack getBucketItemStack(){
   		return new ItemStack(CaerulaArborModItems.BUCKET_AXOLOTL.get());
   	}

   	public SoundEvent getPickupSound(){
   		return SoundEvents.BUCKET_FILL_AXOLOTL;
   	}

   	public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
      return Bucketable.bucketMobPickup(pPlayer, pHand, this).orElse(super.mobInteract(pPlayer, pHand));
   }

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "axolotl_undertides");
		this.entityData.define(DATA_FAKE_DEATH_T, 0);
		this.entityData.define(DATA_FAKE_D_COOLDOWN, 0);
		this.entityData.define(DATA_HEAL_PER_S, 33);
		this.entityData.define(DATA_LAST_HELPER_UUID, "");
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
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Drowned.class, true, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Guardian.class, true, false));
		this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = PromotedAxolotlEntity.this.getX();
				double y = PromotedAxolotlEntity.this.getY();
				double z = PromotedAxolotlEntity.this.getZ();
				Entity entity = PromotedAxolotlEntity.this;
				Level world = PromotedAxolotlEntity.this.level();
				return super.canUse() && AxolotlFakingDeathProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = PromotedAxolotlEntity.this.getX();
				double y = PromotedAxolotlEntity.this.getY();
				double z = PromotedAxolotlEntity.this.getZ();
				Entity entity = PromotedAxolotlEntity.this;
				Level world = PromotedAxolotlEntity.this.level();
				return super.canContinueToUse() && AxolotlFakingDeathProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1, 40) {
			@Override
			public boolean canUse() {
				double x = PromotedAxolotlEntity.this.getX();
				double y = PromotedAxolotlEntity.this.getY();
				double z = PromotedAxolotlEntity.this.getZ();
				Entity entity = PromotedAxolotlEntity.this;
				Level world = PromotedAxolotlEntity.this.level();
				return super.canUse() && AxolotlFakingDeathProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = PromotedAxolotlEntity.this.getX();
				double y = PromotedAxolotlEntity.this.getY();
				double z = PromotedAxolotlEntity.this.getZ();
				Entity entity = PromotedAxolotlEntity.this;
				Level world = PromotedAxolotlEntity.this.level();
				return super.canContinueToUse() && AxolotlFakingDeathProcedure.execute(entity);
			}
		});

		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = PromotedAxolotlEntity.this.getX();
				double y = PromotedAxolotlEntity.this.getY();
				double z = PromotedAxolotlEntity.this.getZ();
				Entity entity = PromotedAxolotlEntity.this;
				Level world = PromotedAxolotlEntity.this.level();
				return super.canUse() && AxolotlSpawnProcedure.execute(world);
			}

			@Override
			public boolean canContinueToUse() {
				double x = PromotedAxolotlEntity.this.getX();
				double y = PromotedAxolotlEntity.this.getY();
				double z = PromotedAxolotlEntity.this.getZ();
				Entity entity = PromotedAxolotlEntity.this;
				Level world = PromotedAxolotlEntity.this.level();
				return super.canContinueToUse() && AxolotlSpawnProcedure.execute(world);
			}
		});
		this.goalSelector.addGoal(1, new PromotedAxolotlEntity.RangedAttackGoal(this, 1.25, 40, 16f) {
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
				return AxolotlFakingDeathProcedure.execute(this.mob);
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
			((PromotedAxolotlEntity) rangedAttackMob).entityData.set(SHOOT, false);
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
					((PromotedAxolotlEntity) rangedAttackMob).entityData.set(SHOOT, false);
					return;
				}
				((PromotedAxolotlEntity) rangedAttackMob).entityData.set(SHOOT, true);
				float f = (float) Math.sqrt(d0) / this.attackRadius;
				float f1 = Mth.clamp(f, 0.1F, 1.0F);
				this.rangedAttackMob.performRangedAttack(this.target, f1);
				this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
			} else if (this.attackTime < 0) {
				this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) this.attackRadius, (double) this.attackIntervalMin, (double) this.attackIntervalMax));
			} else
				((PromotedAxolotlEntity) rangedAttackMob).entityData.set(SHOOT, false);
		}
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
		if (this.isInWater()) return SoundEvents.AXOLOTL_IDLE_WATER;
		return SoundEvents.AXOLOTL_IDLE_AIR;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return SoundEvents.AXOLOTL_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.AXOLOTL_DEATH;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.CACTUS))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		if (source.is(DamageTypes.LIGHTNING_BOLT))
			amount *= 1000;
		if (entityData.get(DATA_FAKE_DEATH_T) > 0) amount *= 0.75f;
		boolean flag = super.hurt(source, amount);
		if (flag) ReduceAxolotlHealProcedure.execute(this);
		return flag;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		if (reason != MobSpawnType.BUCKET) SetAxolotlTextureProcedure.execute(this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataFAKE_DEATH_T", this.entityData.get(DATA_FAKE_DEATH_T));
		compound.putInt("DataFAKE_D_COOLDOWN", this.entityData.get(DATA_FAKE_D_COOLDOWN));
		compound.putInt("DataHEAL_PER_S", this.entityData.get(DATA_HEAL_PER_S));
		compound.putString("DataLAST_HELPER_UUID", this.entityData.get(DATA_LAST_HELPER_UUID));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataFAKE_DEATH_T"))
			this.entityData.set(DATA_FAKE_DEATH_T, compound.getInt("DataFAKE_DEATH_T"));
		if (compound.contains("DataFAKE_D_COOLDOWN"))
			this.entityData.set(DATA_FAKE_D_COOLDOWN, compound.getInt("DataFAKE_D_COOLDOWN"));
		if (compound.contains("DataHEAL_PER_S"))
			this.entityData.set(DATA_HEAL_PER_S, compound.getInt("DataHEAL_PER_S"));
		if (compound.contains("DataLAST_HELPER_UUID"))
			this.entityData.set(DATA_LAST_HELPER_UUID, compound.getString("DataLAST_HELPER_UUID"));
	}

	@Override
	public void awardKillScore(Entity entity, int score, DamageSource damageSource) {
		super.awardKillScore(entity, score, damageSource);
		RewardAxolotlHealProcedure.execute(this);
	}
	
	public int timeTillNextBlink = 40;
	public int clientBlinkTime = 0;

	@Override
	public void tick(){
		super.tick();
		if (this.level().isClientSide()){
			timeTillNextBlink--;
			clientBlinkTime--;
			if (timeTillNextBlink <= 0){
				clientBlinkTime = 10;
				timeTillNextBlink = Mth.nextInt(this.getRandom(), 40, 90);
			}
		}
	}

	@Override
	public void baseTick() {
		super.baseTick();
		AxolotlTicksProcedure.execute(this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public void performRangedAttack(LivingEntity target, float flval) {
		CaerulaArborMod.queueServerWork(9, ()->{
			FishSplashEntity.shoot(this, target);
			}
		);
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
		PromotedAxolotlEntity retval = CaerulaArborModEntities.PROMOTED_AXOLOTL.get().create(serverWorld);
		retval.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(retval.blockPosition()), MobSpawnType.BREEDING, null, null);
		return retval;
	}

	@Override
	protected void usePlayerItem(Player pPlayer, InteractionHand pHand, ItemStack pStack){
		super.usePlayerItem(pPlayer, pHand, pStack);
		RewardAxolotlHealProcedure.execute(this);
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return List.of(CaerulaArborModItems.COLLECTOR_MEAT.get()).contains(stack.getItem());
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
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 27);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
		builder = builder.add(Attributes.FOLLOW_RANGE, 20);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.3);
		builder = builder.add(ForgeMod.SWIM_SPEED.get(), 0.65);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
			return event.setAndContinue(RawAnimation.begin().thenPlay("die"));
		}
		if (this.animationprocedure.equals("empty")) {
			if (entityData.get(DATA_FAKE_DEATH_T) > 0) return event.setAndContinue(RawAnimation.begin().thenLoop("fake_death"));
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.1F && event.getLimbSwingAmount() < 0.1F))
) {
				if (this.isInWaterOrBubble()) {
					return event.setAndContinue(RawAnimation.begin().thenLoop("swim"));
				}
				return event.setAndContinue(RawAnimation.begin().thenLoop("move"));
			}
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

	private PlayState attackingPredicate(AnimationState event) {
		if (getAttackAnim(event.getPartialTick()) > 0f && !this.swinging) {
			this.swinging = true;
			this.lastSwing = level().getGameTime();
		}
		if (this.swinging && this.lastSwing + 19L <= level().getGameTime()) {
			this.swinging = false;
		}
		if ((this.swinging || this.entityData.get(SHOOT)) && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("attack"));
		}
		return PlayState.CONTINUE;
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
		data.add(new AnimationController<>(this, "attacking", 1, this::attackingPredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	public boolean shouldBlink(){
		if (this.isDeadOrDying()) return false;
		if (this.entityData.get(DATA_FAKE_DEATH_T) > 0) return true; 
		return clientBlinkTime > 0;
	}
}
