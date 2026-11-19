
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

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
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

import net.apocalypse.careunite.procedures.WardenSkillsProcedure;
import net.apocalypse.careunite.procedures.WardenDropExpProcedure;
import net.apocalypse.careunite.procedures.WardenDeathProcedure;
import net.apocalypse.careunite.procedures.IsWardenDurativeProcedure;
import net.apocalypse.careunite.procedures.InitWardenProcedure;
import net.apocalypse.careunite.procedures.WardenDoHurtTargetProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.AlchemyCreation;

import javax.annotation.Nullable;

import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.Difficulty;
import net.minecraft.server.level.ServerLevel;
import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class OceanizedWardenEntity extends Monster implements GeoEntity, AlchemyCreation.InjectableEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(OceanizedWardenEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(OceanizedWardenEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(OceanizedWardenEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_skillp1 = SynchedEntityData.defineId(OceanizedWardenEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_skillp2 = SynchedEntityData.defineId(OceanizedWardenEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_duration = SynchedEntityData.defineId(OceanizedWardenEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.BLUE, ServerBossEvent.BossBarOverlay.NOTCHED_6);

	public OceanizedWardenEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.OCEANIZED_WARDEN.get(), world);
	}

	public OceanizedWardenEntity(EntityType<OceanizedWardenEntity> type, Level world) {
		super(type, world);
		xpReward = 1024;
		setNoAi(false);
		setMaxUpStep(0.6f);
		setPersistenceRequired();
	}

	public int injectionType(){
		return 2;
	}
	public int injectionCost(){
		return 1;
	}
	public boolean onInjection(Player player){
		if (this.getHealth() >= this.getMaxHealth() * 0.75f && this.level() instanceof ServerLevel _level) {
			Entity entityToSpawn = CaerulaArborModEntities.OCEANIZED_WARDENIS.get().spawn(_level, this.blockPosition(), MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				_level.sendParticles(CaerulaArborModParticleTypes.ALCHEMY_PARTICLE.get(), this.getX(), this.getY()+1.5, this.getZ(), 32, 1.5, 1.5, 1.5, 0.1);
				entityToSpawn.setYRot(this.getYRot());
				this.setRemoved(OceanizedWardenEntity.RemovalReason.KILLED);
				return true;
			}
		}
		return false;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "oceanized_warden");
		this.entityData.define(DATA_skillp1, 100);
		this.entityData.define(DATA_skillp2, 120);
		this.entityData.define(DATA_duration, 0);
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
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 2, true) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 5.0625;
			}

			@Override
			public boolean canUse() {
				double x = OceanizedWardenEntity.this.getX();
				double y = OceanizedWardenEntity.this.getY();
				double z = OceanizedWardenEntity.this.getZ();
				Entity entity = OceanizedWardenEntity.this;
				Level world = OceanizedWardenEntity.this.level();
				return super.canUse() && IsWardenDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedWardenEntity.this.getX();
				double y = OceanizedWardenEntity.this.getY();
				double z = OceanizedWardenEntity.this.getZ();
				Entity entity = OceanizedWardenEntity.this;
				Level world = OceanizedWardenEntity.this.level();
				return super.canContinueToUse() && IsWardenDurativeProcedure.execute(entity);
			}

		});
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, SnowGolem.class, true, false));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Villager.class, true, false));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, Illusioner.class, true, false));
		this.targetSelector.addGoal(7, new NearestAttackableTargetGoal(this, Pillager.class, true, false));
		this.targetSelector.addGoal(8, new NearestAttackableTargetGoal(this, Vindicator.class, true, false));
		this.targetSelector.addGoal(9, new NearestAttackableTargetGoal(this, Witch.class, true, false));
		this.targetSelector.addGoal(10, new NearestAttackableTargetGoal(this, Piglin.class, true, false));
		this.targetSelector.addGoal(11, new NearestAttackableTargetGoal(this, PiglinBrute.class, true, false));
		this.targetSelector.addGoal(12, new NearestAttackableTargetGoal(this, ZombifiedPiglin.class, true, false));
		this.goalSelector.addGoal(13, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = OceanizedWardenEntity.this.getX();
				double y = OceanizedWardenEntity.this.getY();
				double z = OceanizedWardenEntity.this.getZ();
				Entity entity = OceanizedWardenEntity.this;
				Level world = OceanizedWardenEntity.this.level();
				return super.canUse() && IsWardenDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedWardenEntity.this.getX();
				double y = OceanizedWardenEntity.this.getY();
				double z = OceanizedWardenEntity.this.getZ();
				Entity entity = OceanizedWardenEntity.this;
				Level world = OceanizedWardenEntity.this.level();
				return super.canContinueToUse() && IsWardenDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(14, new LookAtPlayerGoal(this, Player.class, (float) 9));
		this.goalSelector.addGoal(15, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = OceanizedWardenEntity.this.getX();
				double y = OceanizedWardenEntity.this.getY();
				double z = OceanizedWardenEntity.this.getZ();
				Entity entity = OceanizedWardenEntity.this;
				Level world = OceanizedWardenEntity.this.level();
				return super.canUse() && IsWardenDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedWardenEntity.this.getX();
				double y = OceanizedWardenEntity.this.getY();
				double z = OceanizedWardenEntity.this.getZ();
				Entity entity = OceanizedWardenEntity.this;
				Level world = OceanizedWardenEntity.this.level();
				return super.canContinueToUse() && IsWardenDurativeProcedure.execute(entity);
			}
		});
	}

	@Override
	public MobType getMobType() {
		return MobType.WATER;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.ambient"));
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.step")), 0.15f, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.death"));
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.IN_FIRE))
			return false;
		if (source.is(DamageTypes.FALL))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		return super.hurt(source, amount);
	}

	public int lastAttackTick = 0;
	@Override
	public boolean doHurtTarget(Entity pEntity){
		if (this.tickCount > lastAttackTick && this.tickCount < lastAttackTick + 16) return false;
		lastAttackTick = this.tickCount;
		WardenDoHurtTargetProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), pEntity, this);
		return true;
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		WardenDeathProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		InitWardenProcedure.execute(this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("Dataskillp1", this.entityData.get(DATA_skillp1));
		compound.putInt("Dataskillp2", this.entityData.get(DATA_skillp2));
		compound.putInt("Dataduration", this.entityData.get(DATA_duration));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("Dataskillp1"))
			this.entityData.set(DATA_skillp1, compound.getInt("Dataskillp1"));
		if (compound.contains("Dataskillp2"))
			this.entityData.set(DATA_skillp2, compound.getInt("Dataskillp2"));
		if (compound.contains("Dataduration"))
			this.entityData.set(DATA_duration, compound.getInt("Dataduration"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		WardenSkillsProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
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

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.15);
		builder = builder.add(Attributes.MAX_HEALTH, 825);
		builder = builder.add(Attributes.ARMOR, 10);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 50);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 10);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))

					&& !this.isAggressive()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_warden.move"));
			}
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.oceanized_warden.die"));
			}
			if (this.isAggressive() && event.isMoving()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_warden.sprint"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_warden.idle"));
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
		if (this.swinging && this.lastSwing + 18L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.oceanized_warden.attack"));
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
		if (this.deathTime == 50) {
			this.remove(OceanizedWardenEntity.RemovalReason.KILLED);
			this.dropExperience();
			WardenDropExpProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ());
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
        super.setHealth(reduction >= mhlth * 0.3f ? hlth - mhlth * 0.3f : hlth - reduction);
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

	public boolean isInv(){
		return this.entityData.get(DATA_duration) > 0;
	}
}
