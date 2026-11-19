
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
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
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

import net.apocalypse.careunite.procedures.IsChimeraDurativeProcedure;
import net.apocalypse.careunite.procedures.InitChimeraProcedure;
import net.apocalypse.careunite.procedures.DropHimselfProcedure;
import net.apocalypse.careunite.procedures.ChimeraSkillsProcedure;
import net.apocalypse.careunite.procedures.CallSeabornAroundProcedure;
import net.apocalypse.careunite.procedures.TideChimeraDoHurtTargetProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import javax.annotation.Nullable;

public class TideChimeraEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(TideChimeraEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(TideChimeraEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(TideChimeraEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_duration = SynchedEntityData.defineId(TideChimeraEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_summonP = SynchedEntityData.defineId(TideChimeraEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_skillP = SynchedEntityData.defineId(TideChimeraEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_deal = SynchedEntityData.defineId(TideChimeraEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.BLUE, ServerBossEvent.BossBarOverlay.NOTCHED_12);

	public TideChimeraEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.TIDE_CHIMERA.get(), world);
	}

	public TideChimeraEntity(EntityType<TideChimeraEntity> type, Level world) {
		super(type, world);
		xpReward = 99;
		setNoAi(false);
		setMaxUpStep(1.5f);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "general_seaborns");
		this.entityData.define(DATA_duration, 0);
		this.entityData.define(DATA_summonP, 3);
		this.entityData.define(DATA_skillP, 200);
		this.entityData.define(DATA_deal, 0);
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
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 16;
			}

			@Override
			public boolean canUse() {
				double x = TideChimeraEntity.this.getX();
				double y = TideChimeraEntity.this.getY();
				double z = TideChimeraEntity.this.getZ();
				Entity entity = TideChimeraEntity.this;
				Level world = TideChimeraEntity.this.level();
				return super.canUse() && IsChimeraDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = TideChimeraEntity.this.getX();
				double y = TideChimeraEntity.this.getY();
				double z = TideChimeraEntity.this.getZ();
				Entity entity = TideChimeraEntity.this;
				Level world = TideChimeraEntity.this.level();
				return super.canContinueToUse() && IsChimeraDurativeProcedure.execute(entity);
			}

		});
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, SnowGolem.class, true, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, IronGolem.class, true, false));
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = TideChimeraEntity.this.getX();
				double y = TideChimeraEntity.this.getY();
				double z = TideChimeraEntity.this.getZ();
				Entity entity = TideChimeraEntity.this;
				Level world = TideChimeraEntity.this.level();
				return super.canUse() && IsChimeraDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = TideChimeraEntity.this.getX();
				double y = TideChimeraEntity.this.getY();
				double z = TideChimeraEntity.this.getZ();
				Entity entity = TideChimeraEntity.this;
				Level world = TideChimeraEntity.this.level();
				return super.canContinueToUse() && IsChimeraDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = TideChimeraEntity.this.getX();
				double y = TideChimeraEntity.this.getY();
				double z = TideChimeraEntity.this.getZ();
				Entity entity = TideChimeraEntity.this;
				Level world = TideChimeraEntity.this.level();
				return super.canUse() && IsChimeraDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = TideChimeraEntity.this.getX();
				double y = TideChimeraEntity.this.getY();
				double z = TideChimeraEntity.this.getZ();
				Entity entity = TideChimeraEntity.this;
				Level world = TideChimeraEntity.this.level();
				return super.canContinueToUse() && IsChimeraDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(7, new FloatGoal(this));
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
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:seaborn_generic_hit"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:apocata_die"));
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		CallSeabornAroundProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this, source.getEntity());
		if (source.is(DamageTypes.FALL))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		return super.hurt(source, amount);
	}

	@Override
	public boolean doHurtTarget(Entity pEntity){
		TideChimeraDoHurtTargetProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), pEntity, this);
		return true;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		InitChimeraProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("Dataduration", this.entityData.get(DATA_duration));
		compound.putInt("DatasummonP", this.entityData.get(DATA_summonP));
		compound.putInt("DataskillP", this.entityData.get(DATA_skillP));
		compound.putInt("Datadeal", this.entityData.get(DATA_deal));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("Dataduration"))
			this.entityData.set(DATA_duration, compound.getInt("Dataduration"));
		if (compound.contains("DatasummonP"))
			this.entityData.set(DATA_summonP, compound.getInt("DatasummonP"));
		if (compound.contains("DataskillP"))
			this.entityData.set(DATA_skillP, compound.getInt("DataskillP"));
		if (compound.contains("Datadeal"))
			this.entityData.set(DATA_deal, compound.getInt("Datadeal"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		ChimeraSkillsProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
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

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.18);
		builder = builder.add(Attributes.MAX_HEALTH, 300);
		builder = builder.add(Attributes.ARMOR, 8);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 19);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 10);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))

			) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.super_apocata.move"));
			}
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.super_apocata.die"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.super_apocata.idle"));
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
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.super_apocata.attack"));
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
			this.remove(TideChimeraEntity.RemovalReason.KILLED);
			this.dropExperience();
			DropHimselfProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ());
		}
	}

	@Override
    public void setHealth(float pHealth){
    	float hlth = this.getHealth();
    	float mhlth = this.getMaxHealth();
        if(this.hasEffect(CaerulaArborModMobEffects.INVULNERABLE.get()) && pHealth < this.getHealth()) return;
        float reduction = hlth - pHealth;
        float finalV = reduction >= mhlth * 0.26f ? hlth - mhlth * 0.26f : hlth - reduction;
        super.setHealth(finalV);
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
