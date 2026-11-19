
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Animal;
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
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.procedures.SkipReborningProcedure;
import net.apocalypse.careunite.procedures.OceanWitherTickProcedure;
import net.apocalypse.careunite.procedures.IsWitherDurativeProcedure;
import net.apocalypse.careunite.procedures.InitOceanWitherProcedure;
import net.apocalypse.careunite.procedures.DropMoistStarProcedure;
import net.apocalypse.careunite.procedures.IsValidForManProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import javax.annotation.Nullable;

import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.Difficulty;

import java.util.EnumSet;
import net.minecraft.server.level.ServerLevel;

public class OceanizedWitherEntity extends Monster implements RangedAttackMob, GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(OceanizedWitherEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(OceanizedWitherEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(OceanizedWitherEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_skillp = SynchedEntityData.defineId(OceanizedWitherEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_duration = SynchedEntityData.defineId(OceanizedWitherEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_spawn = SynchedEntityData.defineId(OceanizedWitherEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_shelled = SynchedEntityData.defineId(OceanizedWitherEntity.class, EntityDataSerializers.BOOLEAN);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.WHITE, ServerBossEvent.BossBarOverlay.NOTCHED_10);

	private boolean summoned = false;

	public OceanizedWitherEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.OCEANIZED_WITHER.get(), world);
	}

	public OceanizedWitherEntity(EntityType<OceanizedWitherEntity> type, Level world) {
		super(type, world);
		xpReward = 512;
		setNoAi(false);
		setMaxUpStep(2f);
		setPersistenceRequired();
		this.moveControl = new FlyingMoveControl(this, 10, true);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "oceanized_wither_inv");
		this.entityData.define(DATA_skillp, 200);
		this.entityData.define(DATA_duration, 100);
		this.entityData.define(DATA_spawn, 0);
		this.entityData.define(DATA_shelled, false);
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public String getTexture() {
		return this.entityData.get(TEXTURE);
	}

	@Override
	protected float getSoundVolume() {
      return 3F;
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
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Villager.class, false, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Animal.class, false, false));
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1, 20) {
			@Override
			protected Vec3 getPosition() {
				RandomSource random = OceanizedWitherEntity.this.getRandom();
				double dir_x = OceanizedWitherEntity.this.getX() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_y = OceanizedWitherEntity.this.getY() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_z = OceanizedWitherEntity.this.getZ() + ((random.nextFloat() * 2 - 1) * 16);
				return new Vec3(dir_x, dir_y, dir_z);
			}

			@Override
			public boolean canUse() {
				double x = OceanizedWitherEntity.this.getX();
				double y = OceanizedWitherEntity.this.getY();
				double z = OceanizedWitherEntity.this.getZ();
				Entity entity = OceanizedWitherEntity.this;
				Level world = OceanizedWitherEntity.this.level();
				return super.canUse() && IsWitherDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedWitherEntity.this.getX();
				double y = OceanizedWitherEntity.this.getY();
				double z = OceanizedWitherEntity.this.getZ();
				Entity entity = OceanizedWitherEntity.this;
				Level world = OceanizedWitherEntity.this.level();
				return super.canContinueToUse() && IsWitherDurativeProcedure.execute(entity);
			}

		});
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = OceanizedWitherEntity.this.getX();
				double y = OceanizedWitherEntity.this.getY();
				double z = OceanizedWitherEntity.this.getZ();
				Entity entity = OceanizedWitherEntity.this;
				Level world = OceanizedWitherEntity.this.level();
				return super.canUse() && IsWitherDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedWitherEntity.this.getX();
				double y = OceanizedWitherEntity.this.getY();
				double z = OceanizedWitherEntity.this.getZ();
				Entity entity = OceanizedWitherEntity.this;
				Level world = OceanizedWitherEntity.this.level();
				return super.canContinueToUse() && IsWitherDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(1, new OceanizedWitherEntity.RangedAttackGoal(this, 1.25, 30, 12f) {
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
			((OceanizedWitherEntity) rangedAttackMob).entityData.set(SHOOT, false);
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
					((OceanizedWitherEntity) rangedAttackMob).entityData.set(SHOOT, false);
					return;
				}
				((OceanizedWitherEntity) rangedAttackMob).entityData.set(SHOOT, true);
				float f = (float) Math.sqrt(d0) / this.attackRadius;
				float f1 = Mth.clamp(f, 0.1F, 1.0F);
				this.rangedAttackMob.performRangedAttack(this.target, f1);
				this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
			} else if (this.attackTime < 0) {
				this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) this.attackRadius, (double) this.attackIntervalMin, (double) this.attackIntervalMax));
			} else
				((OceanizedWitherEntity) rangedAttackMob).entityData.set(SHOOT, false);
		}
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:ocean_wither_idle"));
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:ocean_wither_hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:ocean_wither_die"));
	}

	@Override
	public boolean causeFallDamage(float l, float d, DamageSource source) {
		return false;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.IN_FIRE))
			return false;
		if (source.is(DamageTypes.FALL))
			return false;
		if (source.is(DamageTypes.CACTUS))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		if (source.is(DamageTypes.LIGHTNING_BOLT))
			return false;
		if (source.is(DamageTypes.EXPLOSION))
			return false;
		if (source.is(DamageTypes.DRAGON_BREATH))
			return false;
		if (source.is(DamageTypes.WITHER))
			return false;
		if (source.is(DamageTypes.WITHER_SKULL))
			return false;
		return super.hurt(source, amount);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		InitOceanWitherProcedure.execute(this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("Dataskillp", this.entityData.get(DATA_skillp));
		compound.putInt("Dataduration", this.entityData.get(DATA_duration));
		compound.putInt("Dataspawn", this.entityData.get(DATA_spawn));
		compound.putBoolean("Datashelled", this.entityData.get(DATA_shelled));
		compound.putBoolean("GuardSummoned", summoned);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("Dataskillp"))
			this.entityData.set(DATA_skillp, compound.getInt("Dataskillp"));
		if (compound.contains("Dataduration"))
			this.entityData.set(DATA_duration, compound.getInt("Dataduration"));
		if (compound.contains("Dataspawn"))
			this.entityData.set(DATA_spawn, compound.getInt("Dataspawn"));
		if (compound.contains("Datashelled"))
			this.entityData.set(DATA_shelled, compound.getBoolean("Datashelled"));
		if (compound.contains("GuardSummoned"))
			this.summoned = compound.getBoolean("GuardSummoned");
	}

	@Override
	public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
		ItemStack itemstack = sourceentity.getItemInHand(hand);
		InteractionResult retval = InteractionResult.sidedSuccess(this.level().isClientSide());
		super.mobInteract(sourceentity, hand);
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();
		Entity entity = this;
		Level world = this.level();
		return SkipReborningProcedure.execute(entity, itemstack);
	}

	@Override
	public void tick(){
		super.tick();
		if (!summoned && this.getHealth() <= this.getMaxHealth() * 0.5){
			summoned = true;
			spawnGuard(this.level(), this.getX(), this.getY(), this.getZ(), this);
		}
	}

	public static void spawnGuard(Level world, double x, double y, double z, Entity entity) {
		if (world instanceof ServerLevel _level) {
			for (int index0 = 0; index0 < 5; index0++) {
				if (IsValidForManProcedure.execute(world, x + 4 - index0, y, z)) {
					summonSyncedIllusion(_level, x + 4 - index0, y, z, entity);
					break;
				}
			}
			for (int index1 = 0; index1 < 5; index1++) {
				if (IsValidForManProcedure.execute(world, x - (4 - index1), y, z)) {
					summonSyncedIllusion(_level,x - (4 - index1), y, z, entity);
					break;
				}
			}
			for (int index2 = 0; index2 < 5; index2++) {
				if (IsValidForManProcedure.execute(world, x, y, z + 4 - index2)) {
					summonSyncedIllusion(_level, x, y, z + 4 - index2, entity);
					break;
				}
			}
			for (int index3 = 0; index3 < 5; index3++) {
				if (IsValidForManProcedure.execute(world, x, y, z - (4 - index3))) {
					summonSyncedIllusion(_level, x, y, z - (4 - index3), entity);
					break;
				}
			}
		}
	}

	public static void summonSyncedIllusion(ServerLevel world, double x, double y, double z, Entity entity){
		Entity entityToSpawn = CaerulaArborModEntities.OCEAN_WITHER_SKELETON.get().spawn(world, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
		if (entityToSpawn instanceof OceanWitherSkeletonEntity skeleton) {
			entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
			skeleton.setOwner(entity);
		}
	}

	@Override
	public void baseTick() {
		super.baseTick();
		OceanWitherTickProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public void performRangedAttack(LivingEntity target, float flval) {
		WitherShootPreEntity.shoot(this, target);
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void doPush(Entity entityIn) {
	}

	@Override
	protected void pushEntities() {
	}

	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	@Override
	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer player) {
		super.stopSeenByPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	@Override
	public void customServerAiStep() {
		super.customServerAiStep();
		this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
	}

	@Override
	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	@Override
	public void setNoGravity(boolean ignored) {
		super.setNoGravity(true);
	}

	public void aiStep() {
		super.aiStep();
		this.setNoGravity(true);
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.MAX_HEALTH, 550);
		builder = builder.add(Attributes.ARMOR, 8);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 12);
		builder = builder.add(Attributes.FOLLOW_RANGE, 56);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 10);
		builder = builder.add(Attributes.FLYING_SPEED, 0.35);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.oceanzied_wither.die"));
			}
			if (this.isAggressive() && event.isMoving()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanzied_wither.aggresive"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanzied_wither.idle"));
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
		if (this.deathTime == 30) {
			DropMoistStarProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
			this.remove(OceanizedWitherEntity.RemovalReason.KILLED);
			this.dropExperience();
		
		}
	}

	@Override
	public void remove(RemovalReason pReason){
		if(this.level().getDifficulty() != Difficulty.PEACEFUL && pReason == RemovalReason.DISCARDED){
			this.hurt(
				new DamageSource(
					this.level().registryAccess().
					registryOrThrow(Registries.DAMAGE_TYPE).
					getHolderOrThrow(
						ResourceKey.create(
							Registries.DAMAGE_TYPE, 
							new ResourceLocation("caerula_arbor_reunite:oceankiller_damage")
						)
					)
				),
				20
			);
			return;
		}
		super.remove(pReason);
	}

	@Override
    public void setHealth(float pHealth){
    	float hlth = this.getHealth();
    	float mhlth = this.getMaxHealth();
        if(this.hasEffect(CaerulaArborModMobEffects.INVULNERABLE.get()) && pHealth < hlth) return;
        float reduction = hlth - pHealth;
        super.setHealth(reduction >= mhlth * 0.35f ? hlth - mhlth * 0.35f : hlth - reduction);
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
		data.add(new AnimationController<>(this, "procedure", 0, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
