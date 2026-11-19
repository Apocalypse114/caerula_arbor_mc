
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
import net.minecraftforge.common.DungeonHooks;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
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

import net.apocalypse.careunite.procedures.RareSeabornSpawnProcedure;
import net.apocalypse.careunite.procedures.OceanizedPlayerProcedure;
import net.apocalypse.careunite.procedures.InitBplusMagicProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.CaerulaArborMod;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModSounds;

import javax.annotation.Nullable;

import java.util.EnumSet;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import java.util.Map;
import net.minecraft.world.item.ItemStack;

public class FleeFishEntity extends Monster implements RangedAttackMob, GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(FleeFishEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(FleeFishEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(FleeFishEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Boolean> DATA_WALK = SynchedEntityData.defineId(FleeFishEntity.class, EntityDataSerializers.BOOLEAN);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public final MoveControl flyControl;
	public final MoveControl walkControl;
	public String animationprocedure = "empty";

	public FleeFishEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.FLEE_FISH.get(), world);
	}

	public FleeFishEntity(EntityType<FleeFishEntity> type, Level world) {
		super(type, world);
		xpReward = 8;
		setNoAi(false);
		setMaxUpStep(0.6f);
		this.flyControl = new FlyingMoveControl(this, 10, true);
		this.walkControl = new MoveControl(this);
		this.moveControl = this.flyControl;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "fleefish");
		this.entityData.define(DATA_WALK, false);
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
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, IronGolem.class, true, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, SnowGolem.class, true, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Villager.class, true, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Illusioner.class, true, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Pillager.class, true, true));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, Vindicator.class, true, true));
		this.targetSelector.addGoal(7, new NearestAttackableTargetGoal(this, Witch.class, true, true));
		this.targetSelector.addGoal(8, new NearestAttackableTargetGoal(this, Piglin.class, true, true));
		this.targetSelector.addGoal(9, new NearestAttackableTargetGoal(this, PiglinBrute.class, true, true));
		this.targetSelector.addGoal(10, new NearestAttackableTargetGoal(this, ZombifiedPiglin.class, true, true));
		this.targetSelector.addGoal(11, new RunFishEntity.AttackPlayerGoal(this));
		this.targetSelector.addGoal(12, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(13, new MeleeAttackGoal(this, 1, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 2.25;
			}
		});
		this.goalSelector.addGoal(14, new RandomStrollGoal(this, 1, 20) {
			@Override
			protected Vec3 getPosition() {
				FleeFishEntity fish = (FleeFishEntity) FleeFishEntity.this;
				if (fish.walking()) return super.getPosition();
				RandomSource random = FleeFishEntity.this.getRandom();
				double dir_x = FleeFishEntity.this.getX() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_y = FleeFishEntity.this.getY() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_z = FleeFishEntity.this.getZ() + ((random.nextFloat() * 2 - 1) * 16);
				return new Vec3(dir_x, dir_y, dir_z);
			}
			@Override
			public boolean canUse() {
				FleeFishEntity fish = (FleeFishEntity) FleeFishEntity.this;
				return super.canUse() && !fish.walking();
			}

		});
		//this.goalSelector.addGoal(15, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(16, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(1, new FleeFishEntity.RangedAttackGoal(this, 1.25, 60, 9f) {
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
			if (this.rangedAttackMob instanceof FleeFishEntity fish && fish.walking()) return false;
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
			((FleeFishEntity) rangedAttackMob).entityData.set(SHOOT, false);
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
					((FleeFishEntity) rangedAttackMob).entityData.set(SHOOT, false);
					return;
				}
				((FleeFishEntity) rangedAttackMob).entityData.set(SHOOT, true);
				float f = (float) Math.sqrt(d0) / this.attackRadius;
				float f1 = Mth.clamp(f, 0.1F, 1.0F);
				this.rangedAttackMob.performRangedAttack(this.target, f1);
				this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
			} else if (this.attackTime < 0) {
				this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) this.attackRadius, (double) this.attackIntervalMin, (double) this.attackIntervalMax));
			} else
				((FleeFishEntity) rangedAttackMob).entityData.set(SHOOT, false);
		}
	}

	@Override
	public MobType getMobType() {
		return MobType.WATER;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return CaerulaArborModSounds.SEABORN_ELITE_HURT.get();
	}

	@Override
	protected float getSoundVolume() {
      return 0.375F;
   }

	@Override
	public SoundEvent getDeathSound() {
		return CaerulaArborModSounds.SEABORN_ELITE_DEATH.get();
	}

	protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
		super.dropCustomDeathLoot(source, looting, recentlyHitIn);
		if(!this.walking())
			this.spawnAtLocation(new ItemStack(CaerulaArborModItems.DRIFTER_BALL.get()));
	}


	@Override
	public boolean causeFallDamage(float l, float d, DamageSource source) {
		return false;
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
		compound.putBoolean("DataWALK", this.entityData.get(DATA_WALK));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataWALK"))
			this.entityData.set(DATA_WALK, compound.getBoolean("DataWALK"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		this.refreshDimensions();
	}

	@Override
	public void tick(){
		super.tick();
		if (!this.level().isClientSide()){
			if (this.walking()) this.moveControl = this.walkControl;
			else this.moveControl = this.flyControl;
		}
	}

	public boolean walking(){
		return this.entityData.get(DATA_WALK);
	}

	public void setWalking(boolean walk){
		this.entityData.set(DATA_WALK, walk);
	}

	public void startWalk(){
		if (this.walking()) return;
		this.setWalking(true);
		AttributeInstance move = this.getAttribute(Attributes.MOVEMENT_SPEED);
		if (move != null){
			move.setBaseValue(move.getBaseValue() * 0.5);
		}
		this.setNoGravity(false);
		this.moveControl = this.walkControl;
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1.2);
	}

	@Override
	public void performRangedAttack(LivingEntity target, float flval) {
		CaerulaArborMod.queueServerWork(3, ()->{
			FleefishBulletEntity.shoot(this, target);
			}
		);
	}

	@Override
	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	@Override
	public boolean addEffect(MobEffectInstance pInstance, Entity pEntity){
		MobEffect effect = pInstance.getEffect();
		if (shouldMakeMeWalk(effect, pInstance.getAmplifier())) this.startWalk();
		return super.addEffect(pInstance, pEntity);
	}

	public boolean shouldMakeMeWalk(MobEffect effect, int amplifier){
		if(effect == CaerulaArborModMobEffects.DIZZY.get()
			|| effect == CaerulaArborModMobEffects.FROZEN.get()
			|| effect == MobEffects.MOVEMENT_SLOWDOWN)
			return true;
		amplifier ++;
        Map<Attribute, AttributeModifier> modifiers = effect.getAttributeModifiers();
        AttributeModifier move = modifiers.get(Attributes.MOVEMENT_SPEED),
                fly = modifiers.get(Attributes.FLYING_SPEED);
        return (move != null && move.getAmount() * amplifier >= 0.8) 
        	|| (fly != null && fly.getAmount() * amplifier >= 0.8);
	}

	@Override
	public void setNoGravity(boolean b) {
		if (this.walking() || this.isDeadOrDying()) super.setNoGravity(b);
		else super.setNoGravity(true);
	}

	public void aiStep() {
		super.aiStep();
		//this.setNoGravity(true);
	}

	public static void init() {
		SpawnPlacements.register(CaerulaArborModEntities.FLEE_FISH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			return RareSeabornSpawnProcedure.execute(world, x, y, z);
		});
		DungeonHooks.addDungeonMob(CaerulaArborModEntities.FLEE_FISH.get(), 180);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.4);
		builder = builder.add(Attributes.MAX_HEALTH, 85);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 18);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.33);
		builder = builder.add(Attributes.FLYING_SPEED, 0.4);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
			if (this.walking())
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.skimming_sea_drifter.die_ground"));
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.skimming_sea_drifter.die"));
		}
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.1F && event.getLimbSwingAmount() < 0.1F))) {
				if(this.walking())
					return event.setAndContinue(RawAnimation.begin().thenLoop("animation.skimming_sea_drifter.move_ground"));
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.skimming_sea_drifter.move"));
			}
			if (this.walking())
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.skimming_sea_drifter.idle_ground"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.skimming_sea_drifter.idle"));
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
		if (this.swinging && this.lastSwing + 11L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (!this.walking() && 
			(this.swinging || this.entityData.get(SHOOT)) && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.skimming_sea_drifter.attack"));
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
			this.remove(FleeFishEntity.RemovalReason.KILLED);
			this.dropExperience();
		}
		this.setNoGravity(false);
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
