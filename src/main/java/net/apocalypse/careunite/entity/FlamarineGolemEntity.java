
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
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
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
import net.minecraft.world.entity.AreaEffectCloud;
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

import net.apocalypse.careunite.procedures.MobAliveProcedure;
import net.apocalypse.careunite.procedures.IsFlamarineDurativeProcedure;
import net.apocalypse.careunite.procedures.InitFlamarineGolemProcedure;
import net.apocalypse.careunite.procedures.FlamarineSkillsProcedure;
import net.apocalypse.careunite.procedures.FlamarineHitProcedure;
import net.apocalypse.careunite.procedures.DropATemplateProcedure;
import net.apocalypse.careunite.procedures.FlamarineDoHurtTargetProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import javax.annotation.Nullable;

public class FlamarineGolemEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(FlamarineGolemEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(FlamarineGolemEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(FlamarineGolemEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_duration = SynchedEntityData.defineId(FlamarineGolemEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_skillP1 = SynchedEntityData.defineId(FlamarineGolemEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_skillP2 = SynchedEntityData.defineId(FlamarineGolemEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_addition = SynchedEntityData.defineId(FlamarineGolemEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.BLUE, ServerBossEvent.BossBarOverlay.PROGRESS);

	public FlamarineGolemEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.FLAMARINE_GOLEM.get(), world);
	}

	public FlamarineGolemEntity(EntityType<FlamarineGolemEntity> type, Level world) {
		super(type, world);
		xpReward = 48;
		setNoAi(false);
		setMaxUpStep(1.5f);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "flamarine_golem");
		this.entityData.define(DATA_duration, 0);
		this.entityData.define(DATA_skillP1, 6);
		this.entityData.define(DATA_skillP2, 100);
		this.entityData.define(DATA_addition, 40);
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
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 2.25, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 20.25;
			}

			@Override
			public boolean canUse() {
				double x = FlamarineGolemEntity.this.getX();
				double y = FlamarineGolemEntity.this.getY();
				double z = FlamarineGolemEntity.this.getZ();
				Entity entity = FlamarineGolemEntity.this;
				Level world = FlamarineGolemEntity.this.level();
				return super.canUse() && IsFlamarineDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = FlamarineGolemEntity.this.getX();
				double y = FlamarineGolemEntity.this.getY();
				double z = FlamarineGolemEntity.this.getZ();
				Entity entity = FlamarineGolemEntity.this;
				Level world = FlamarineGolemEntity.this.level();
				return super.canContinueToUse() && IsFlamarineDurativeProcedure.execute(entity);
			}

		});
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Player.class, true, false));
		this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = FlamarineGolemEntity.this.getX();
				double y = FlamarineGolemEntity.this.getY();
				double z = FlamarineGolemEntity.this.getZ();
				Entity entity = FlamarineGolemEntity.this;
				Level world = FlamarineGolemEntity.this.level();
				return super.canUse() && IsFlamarineDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = FlamarineGolemEntity.this.getX();
				double y = FlamarineGolemEntity.this.getY();
				double z = FlamarineGolemEntity.this.getZ();
				Entity entity = FlamarineGolemEntity.this;
				Level world = FlamarineGolemEntity.this.level();
				return super.canContinueToUse() && IsFlamarineDurativeProcedure.execute(entity);
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
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.step")), 0.15f, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.death"));
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		FlamarineHitProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this, source.getEntity());
		if (source.is(DamageTypes.IN_FIRE))
			return false;
		if (source.getDirectEntity() instanceof ThrownPotion || source.getDirectEntity() instanceof AreaEffectCloud)
			return false;
		if (source.is(DamageTypes.FALL))
			return false;
		if (source.is(DamageTypes.CACTUS))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		if (source.is(DamageTypes.EXPLOSION))
			return false;
		if (source.is(DamageTypes.TRIDENT))
			return false;
		if (source.is(DamageTypes.FALLING_ANVIL))
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
	public boolean doHurtTarget(Entity pEntity){
		FlamarineDoHurtTargetProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), pEntity, this);
		return true;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		InitFlamarineGolemProcedure.execute(this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("Dataduration", this.entityData.get(DATA_duration));
		compound.putInt("DataskillP1", this.entityData.get(DATA_skillP1));
		compound.putInt("DataskillP2", this.entityData.get(DATA_skillP2));
		compound.putInt("Dataaddition", this.entityData.get(DATA_addition));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("Dataduration"))
			this.entityData.set(DATA_duration, compound.getInt("Dataduration"));
		if (compound.contains("DataskillP1"))
			this.entityData.set(DATA_skillP1, compound.getInt("DataskillP1"));
		if (compound.contains("DataskillP2"))
			this.entityData.set(DATA_skillP2, compound.getInt("DataskillP2"));
		if (compound.contains("Dataaddition"))
			this.entityData.set(DATA_addition, compound.getInt("Dataaddition"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		FlamarineSkillsProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1.25);
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.18);
		builder = builder.add(Attributes.MAX_HEALTH, 270);
		builder = builder.add(Attributes.ARMOR, 23);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 19);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 10);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.flamarine_golem.die"));
			}

		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))

					&& !this.isAggressive() && !this.isSprinting()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.flamarine_golem.move"));
			}
			if (this.isSprinting()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.flamarine_golem.sprint"));
			}
			if (this.isAggressive() && event.isMoving()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.flamarine_golem.sprint"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.flamarine_golem.idle"));
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
		if (this.swinging && this.lastSwing + 35L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.flamarine_golem.attack"));
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
			this.remove(FlamarineGolemEntity.RemovalReason.KILLED);
			this.dropExperience();
			DropATemplateProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ());
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
