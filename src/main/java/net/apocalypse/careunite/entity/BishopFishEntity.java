
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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.OceanizedPlayerProcedure;
import net.apocalypse.careunite.procedures.IsBishopStartedProcedure;
import net.apocalypse.careunite.procedures.InitBishopProcedure;
import net.apocalypse.careunite.procedures.DropRelicBishopProcedure;
import net.apocalypse.careunite.procedures.BishopTicksProcedure;
import net.apocalypse.careunite.procedures.BishopHurtProcedure;
import net.apocalypse.careunite.procedures.BishopDoHurtTargetProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModSounds;

import javax.annotation.Nullable;

public class BishopFishEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(BishopFishEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(BishopFishEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(BishopFishEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_sklp = SynchedEntityData.defineId(BishopFishEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_endp = SynchedEntityData.defineId(BishopFishEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_locx = SynchedEntityData.defineId(BishopFishEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_locy = SynchedEntityData.defineId(BishopFishEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_locz = SynchedEntityData.defineId(BishopFishEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_summonp = SynchedEntityData.defineId(BishopFishEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_duration = SynchedEntityData.defineId(BishopFishEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.BLUE, ServerBossEvent.BossBarOverlay.NOTCHED_10);

	public BishopFishEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.BISHOP_FISH.get(), world);
	}

	public BishopFishEntity(EntityType<BishopFishEntity> type, Level world) {
		super(type, world);
		xpReward = 64;
		setNoAi(false);
		setMaxUpStep(2f);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "bishop");
		this.entityData.define(DATA_sklp, 200);
		this.entityData.define(DATA_endp, 1200);
		this.entityData.define(DATA_locx, 0);
		this.entityData.define(DATA_locy, 0);
		this.entityData.define(DATA_locz, 0);
		this.entityData.define(DATA_summonp, 280);
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
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 0, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 144;
			}

			@Override
			public boolean canUse() {
				double x = BishopFishEntity.this.getX();
				double y = BishopFishEntity.this.getY();
				double z = BishopFishEntity.this.getZ();
				Entity entity = BishopFishEntity.this;
				Level world = BishopFishEntity.this.level();
				return super.canUse() && IsBishopStartedProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = BishopFishEntity.this.getX();
				double y = BishopFishEntity.this.getY();
				double z = BishopFishEntity.this.getZ();
				Entity entity = BishopFishEntity.this;
				Level world = BishopFishEntity.this.level();
				return super.canContinueToUse() && IsBishopStartedProcedure.execute(entity);
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
		this.targetSelector.addGoal(13, new RunFishEntity.AttackPlayerGoal(this));
		this.goalSelector.addGoal(14, new RandomLookAroundGoal(this));
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
		return CaerulaArborModSounds.SEABORN_MASSIVE_AMBIENT.get();
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return CaerulaArborModSounds.SEABORN_MASSIVE_HURT.get();
	}

	@Override
	public SoundEvent getDeathSound() {
		return CaerulaArborModSounds.SEABORN_MASSIVE_DEATH.get();
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		BishopHurtProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		if (source.is(DamageTypes.FALL))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		if (source.is(DamageTypes.LIGHTNING_BOLT))
			return false;
		if (source.is(DamageTypes.FALLING_ANVIL))
			return false;
		return super.hurt(source, amount);
	}

	@Override
	public boolean doHurtTarget(Entity pEntity){
		BishopDoHurtTargetProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		return true;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		InitBishopProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("Datasklp", this.entityData.get(DATA_sklp));
		compound.putInt("Dataendp", this.entityData.get(DATA_endp));
		compound.putInt("Datalocx", this.entityData.get(DATA_locx));
		compound.putInt("Datalocy", this.entityData.get(DATA_locy));
		compound.putInt("Datalocz", this.entityData.get(DATA_locz));
		compound.putInt("Datasummonp", this.entityData.get(DATA_summonp));
		compound.putInt("Dataduration", this.entityData.get(DATA_duration));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("Datasklp"))
			this.entityData.set(DATA_sklp, compound.getInt("Datasklp"));
		if (compound.contains("Dataendp"))
			this.entityData.set(DATA_endp, compound.getInt("Dataendp"));
		if (compound.contains("Datalocx"))
			this.entityData.set(DATA_locx, compound.getInt("Datalocx"));
		if (compound.contains("Datalocy"))
			this.entityData.set(DATA_locy, compound.getInt("Datalocy"));
		if (compound.contains("Datalocz"))
			this.entityData.set(DATA_locz, compound.getInt("Datalocz"));
		if (compound.contains("Datasummonp"))
			this.entityData.set(DATA_summonp, compound.getInt("Datasummonp"));
		if (compound.contains("Dataduration"))
			this.entityData.set(DATA_duration, compound.getInt("Dataduration"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		BishopTicksProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 5);
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

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
		builder = builder.add(Attributes.MAX_HEALTH, 560);
		builder = builder.add(Attributes.ARMOR, 8);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 7);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 10);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.bishop.die"));
		}
		if (this.animationprocedure.equals("empty")) {
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.bishop.idle"));
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
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.bishop.attack"));
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
		if (this.deathTime == 40) {
			this.remove(BishopFishEntity.RemovalReason.KILLED);
			this.dropExperience();
			DropRelicBishopProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ());
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
