
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.util.RandomSource;
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

import net.apocalypse.careunite.procedures.WitheriaTickProcedure;
import net.apocalypse.careunite.procedures.IsWitherDurativeProcedure;
import net.apocalypse.careunite.procedures.InitWitheriaProcedure;
import net.apocalypse.careunite.procedures.DropMoistStarProcedure;
import net.apocalypse.careunite.procedures.WitheriaDoHurtTargetProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import javax.annotation.Nullable;

import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.Difficulty;

public class OceannizedWitheriaEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(OceannizedWitheriaEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(OceannizedWitheriaEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(OceannizedWitheriaEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_skillp = SynchedEntityData.defineId(OceannizedWitheriaEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_duration = SynchedEntityData.defineId(OceannizedWitheriaEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_shelled = SynchedEntityData.defineId(OceannizedWitheriaEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_idle_time = SynchedEntityData.defineId(OceannizedWitheriaEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.WHITE, ServerBossEvent.BossBarOverlay.NOTCHED_10);

	public OceannizedWitheriaEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.OCEANIZED_WITHERIA.get(), world);
	}

	public OceannizedWitheriaEntity(EntityType<OceannizedWitheriaEntity> type, Level world) {
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
		this.entityData.define(TEXTURE, "oceanized_witheria");
		this.entityData.define(DATA_skillp, 100);
		this.entityData.define(DATA_duration, 95);
		this.entityData.define(DATA_shelled, false);
		this.entityData.define(DATA_idle_time, 0);
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
		return new FlyingPathNavigation(this, world);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.25, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 12.25;
			}

			@Override
			public boolean canUse() {
				double x = OceannizedWitheriaEntity.this.getX();
				double y = OceannizedWitheriaEntity.this.getY();
				double z = OceannizedWitheriaEntity.this.getZ();
				Entity entity = OceannizedWitheriaEntity.this;
				Level world = OceannizedWitheriaEntity.this.level();
				return super.canUse() && IsWitherDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceannizedWitheriaEntity.this.getX();
				double y = OceannizedWitheriaEntity.this.getY();
				double z = OceannizedWitheriaEntity.this.getZ();
				Entity entity = OceannizedWitheriaEntity.this;
				Level world = OceannizedWitheriaEntity.this.level();
				return super.canContinueToUse() && IsWitherDurativeProcedure.execute(entity);
			}

		});
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, false, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, SnowGolem.class, false, false));
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1, 20) {
			@Override
			protected Vec3 getPosition() {
				RandomSource random = OceannizedWitheriaEntity.this.getRandom();
				double dir_x = OceannizedWitheriaEntity.this.getX() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_y = OceannizedWitheriaEntity.this.getY() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_z = OceannizedWitheriaEntity.this.getZ() + ((random.nextFloat() * 2 - 1) * 16);
				return new Vec3(dir_x, dir_y, dir_z);
			}

			@Override
			public boolean canUse() {
				double x = OceannizedWitheriaEntity.this.getX();
				double y = OceannizedWitheriaEntity.this.getY();
				double z = OceannizedWitheriaEntity.this.getZ();
				Entity entity = OceannizedWitheriaEntity.this;
				Level world = OceannizedWitheriaEntity.this.level();
				return super.canUse() && IsWitherDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceannizedWitheriaEntity.this.getX();
				double y = OceannizedWitheriaEntity.this.getY();
				double z = OceannizedWitheriaEntity.this.getZ();
				Entity entity = OceannizedWitheriaEntity.this;
				Level world = OceannizedWitheriaEntity.this.level();
				return super.canContinueToUse() && IsWitherDurativeProcedure.execute(entity);
			}

		});
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = OceannizedWitheriaEntity.this.getX();
				double y = OceannizedWitheriaEntity.this.getY();
				double z = OceannizedWitheriaEntity.this.getZ();
				Entity entity = OceannizedWitheriaEntity.this;
				Level world = OceannizedWitheriaEntity.this.level();
				return super.canUse() && IsWitherDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceannizedWitheriaEntity.this.getX();
				double y = OceannizedWitheriaEntity.this.getY();
				double z = OceannizedWitheriaEntity.this.getZ();
				Entity entity = OceannizedWitheriaEntity.this;
				Level world = OceannizedWitheriaEntity.this.level();
				return super.canContinueToUse() && IsWitherDurativeProcedure.execute(entity);
			}
		});
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
	
	public int lastAttackTick = 0;
	@Override
	public boolean doHurtTarget(Entity pEntity){
		if (this.tickCount > lastAttackTick && this.tickCount < lastAttackTick + 16) return false;
		lastAttackTick = this.tickCount;
		WitheriaDoHurtTargetProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), pEntity, this);
		return true;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		InitWitheriaProcedure.execute(this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("Dataskillp", this.entityData.get(DATA_skillp));
		compound.putInt("Dataduration", this.entityData.get(DATA_duration));
		compound.putBoolean("Datashelled", this.entityData.get(DATA_shelled));
		compound.putInt("Dataidle_time", this.entityData.get(DATA_idle_time));
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
		if (compound.contains("Datashelled"))
			this.entityData.set(DATA_shelled, compound.getBoolean("Datashelled"));
		if (compound.contains("Dataidle_time"))
			this.entityData.set(DATA_idle_time, compound.getInt("Dataidle_time"));
		if (compound.contains("GuardSummoned"))
			this.summoned = compound.getBoolean("GuardSummoned");
	}

	private boolean summoned = false;
	@Override
	public void tick(){
		super.tick();
		if (!summoned && this.getHealth() <= this.getMaxHealth() * 0.5){
			summoned = true;
			OceanizedWitherEntity.spawnGuard(this.level(), this.getX(), this.getY(), this.getZ(), this);
		}
	}

	@Override
	protected float getSoundVolume() {
      return 3F;
   }

	@Override
	public void baseTick() {
		super.baseTick();
		WitheriaTickProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.45);
		builder = builder.add(Attributes.MAX_HEALTH, 400);
		builder = builder.add(Attributes.ARMOR, 15);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 24);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 10);
		builder = builder.add(Attributes.FLYING_SPEED, 0.45);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.oceanzied_witheria.die"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanzied_witheria.idle"));
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
		if (this.swinging && this.lastSwing + 19L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			if (this.level().getRandom().nextDouble() < 0.5)
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.oceanzied_witheria.attack_1"));
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.oceanzied_witheria.attack"));
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
		if (this.deathTime == 60) {
			this.remove(OceannizedWitheriaEntity.RemovalReason.KILLED);
			this.dropExperience();
			DropMoistStarProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		}
	}

	@Override
	public void remove(RemovalReason pReason){
		if(this.getEntityData().get(DATA_duration) > 999) super.remove(pReason);
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
		data.add(new AnimationController<>(this, "attacking", 0, this::attackingPredicate));
		data.add(new AnimationController<>(this, "procedure", 0, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
