
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

import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
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

import net.apocalypse.careunite.procedures.ThirsterSkillsProcedure;
import net.apocalypse.careunite.procedures.OutPutThirsterInfoProcedure;
import net.apocalypse.careunite.procedures.IsThirsterDurativeProcedure;
import net.apocalypse.careunite.procedures.InitThirsterProcedure;
import net.apocalypse.careunite.procedures.ThirsterAddIntegProcedure;
import net.apocalypse.careunite.procedures.GetLivingBarrierBarProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModSounds;

import javax.annotation.Nullable;
import net.minecraft.sounds.SoundSource;

public class ThirsterEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(ThirsterEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(ThirsterEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(ThirsterEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_DURATION = SynchedEntityData.defineId(ThirsterEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_SKILL_P = SynchedEntityData.defineId(ThirsterEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_INTEGRATION = SynchedEntityData.defineId(ThirsterEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_DIZZY_NUM = SynchedEntityData.defineId(ThirsterEntity.class, EntityDataSerializers.INT);
	//public static final EntityDataAccessor<Integer> BARRIER_NOTE = SynchedEntityData.defineId(ThirsterEntity.class, EntityDataSerializers.INT);

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.BLUE, ServerBossEvent.BossBarOverlay.PROGRESS);

	public ThirsterEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.THIRSTER.get(), world);
	}

	public ThirsterEntity(EntityType<ThirsterEntity> type, Level world) {
		super(type, world);
		xpReward = 24;
		setNoAi(false);
		setMaxUpStep(1.25f);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "thirster");
		this.entityData.define(DATA_DURATION, 0);
		this.entityData.define(DATA_SKILL_P, 0);
		this.entityData.define(DATA_INTEGRATION, 0);
		this.entityData.define(DATA_DIZZY_NUM, 2);
		//this.entityData.define(BARRIER_NOTE, 0);
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
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.1, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 4;
			}
		});
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = ThirsterEntity.this.getX();
				double y = ThirsterEntity.this.getY();
				double z = ThirsterEntity.this.getZ();
				Entity entity = ThirsterEntity.this;
				Level world = ThirsterEntity.this.level();
				return super.canUse() && IsThirsterDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = ThirsterEntity.this.getX();
				double y = ThirsterEntity.this.getY();
				double z = ThirsterEntity.this.getZ();
				Entity entity = ThirsterEntity.this;
				Level world = ThirsterEntity.this.level();
				return super.canContinueToUse() && IsThirsterDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = ThirsterEntity.this.getX();
				double y = ThirsterEntity.this.getY();
				double z = ThirsterEntity.this.getZ();
				Entity entity = ThirsterEntity.this;
				Level world = ThirsterEntity.this.level();
				return super.canUse() && IsThirsterDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = ThirsterEntity.this.getX();
				double y = ThirsterEntity.this.getY();
				double z = ThirsterEntity.this.getZ();
				Entity entity = ThirsterEntity.this;
				Level world = ThirsterEntity.this.level();
				return super.canContinueToUse() && IsThirsterDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(5, new FloatGoal(this));
	}

	@Override
	public MobType getMobType() {
		return MobType.WATER;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	public SoundEvent HURT = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:seaborn_generic_hit"));
	public SoundEvent DIE = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:seaborn_death"));
	
	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return DIE;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.DROWN))
			return false;
		return super.hurt(source, amount);
	}

	@Override
	public boolean doHurtTarget(Entity pEntity){
		Level _level = this.level();
		if (!_level.isClientSide()) {
			_level.playSound(null, this.blockPosition(), CaerulaArborModSounds.THIRSTER_ATTACK.get(), SoundSource.HOSTILE, 
				2.5f, 0.9f + 0.2f * _level.getRandom().nextFloat());
		} else {
			_level.playLocalSound(this.getX(), this.getY(), this.getZ(), CaerulaArborModSounds.THIRSTER_ATTACK.get(), SoundSource.HOSTILE, 
				2.5f, 0.9f + 0.2f * _level.getRandom().nextFloat(), false);
		}
		return super.doHurtTarget(pEntity);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		InitThirsterProcedure.execute(this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataDURATION", this.entityData.get(DATA_DURATION));
		compound.putInt("DataSKILL_P", this.entityData.get(DATA_SKILL_P));
		compound.putInt("DataINTEGRATION", this.entityData.get(DATA_INTEGRATION));
		compound.putInt("DataDIZZY_NUM", this.entityData.get(DATA_DIZZY_NUM));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataDURATION"))
			this.entityData.set(DATA_DURATION, compound.getInt("DataDURATION"));
		if (compound.contains("DataSKILL_P"))
			this.entityData.set(DATA_SKILL_P, compound.getInt("DataSKILL_P"));
		if (compound.contains("DataINTEGRATION"))
			this.entityData.set(DATA_INTEGRATION, compound.getInt("DataINTEGRATION"));
		if (compound.contains("DataDIZZY_NUM"))
			this.entityData.set(DATA_DIZZY_NUM, compound.getInt("DataDIZZY_NUM"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		ThirsterSkillsProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1.2);
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
		float p = (float) this.entityData.get(DATA_DURATION) / 400f;
		if (p > 0 && !this.isDeadOrDying()) {
			this.bossInfo.setColor(ServerBossEvent.BossBarColor.WHITE);
			this.bossInfo.setProgress(p);
		} else {
			this.bossInfo.setColor(ServerBossEvent.BossBarColor.BLUE);
			float m = this.getMaxHealth();
			if(m > 0) this.bossInfo.setProgress(this.getHealth() / m);
		}
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 340);
		builder = builder.add(Attributes.ARMOR, 15);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 36);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))

			) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.thirster.move"));
			}
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.thirster.die"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.thirster.idle"));
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
		if (this.swinging && this.lastSwing + 13L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.thirster.attack"));
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
			this.remove(ThirsterEntity.RemovalReason.KILLED);
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
		data.add(new AnimationController<>(this, "movement", 1, this::movementPredicate));
		data.add(new AnimationController<>(this, "attacking", 1, this::attackingPredicate));
		data.add(new AnimationController<>(this, "procedure", 1, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
