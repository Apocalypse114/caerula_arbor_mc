
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
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.Goal;
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
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.MegaStoneCutterTicksProcedure;
import net.apocalypse.careunite.procedures.InitMegaStonecutterProcedure;
import net.apocalypse.careunite.procedures.GetEntityCosineProcedure;
import net.apocalypse.careunite.procedures.DropManyStonecutterProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.CaerulaArborMod;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import javax.annotation.Nullable;

import java.util.EnumSet;
import net.minecraft.sounds.SoundEvents;

import java.util.List;
import net.minecraft.world.phys.AABB;
import java.util.Comparator;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;

public class MegaStonecutterEntity extends Monster implements RangedAttackMob, GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(MegaStonecutterEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(MegaStonecutterEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(MegaStonecutterEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_SKILL_TICk = SynchedEntityData.defineId(MegaStonecutterEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_SKILL_COOLDOWN = SynchedEntityData.defineId(MegaStonecutterEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_SHOOTINGGG = SynchedEntityData.defineId(MegaStonecutterEntity.class, EntityDataSerializers.BOOLEAN);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.RED, ServerBossEvent.BossBarOverlay.PROGRESS);

	public MegaStonecutterEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.MEGA_STONECUTTER.get(), world);
	}

	public MegaStonecutterEntity(EntityType<MegaStonecutterEntity> type, Level world) {
		super(type, world);
		xpReward = 888;
		setNoAi(false);
		setMaxUpStep(1f);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "mega_stonecutter");
		this.entityData.define(DATA_SKILL_TICk, 0);
		this.entityData.define(DATA_SKILL_COOLDOWN, 0);
		this.entityData.define(DATA_SHOOTINGGG, false);
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
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Mob.class, false, false));
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(5, new FloatGoal(this));
		this.goalSelector.addGoal(1, new MegaStonecutterEntity.RangedAttackGoal(this, 1, 40, 16f) {
			@Override
			public boolean canContinueToUse() {
				return this.canUse();
			}
		});
	}

	public class RangedAttackGoal extends Goal {
		private final MegaStonecutterEntity mob;
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

		public RangedAttackGoal(MegaStonecutterEntity p_25768_, double p_25769_, int p_25770_, float p_25771_) {
			this(p_25768_, p_25769_, p_25770_, p_25770_, p_25771_);
		}

		public RangedAttackGoal(MegaStonecutterEntity p_25773_, double p_25774_, int p_25775_, int p_25776_, float p_25777_) {
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
			((MegaStonecutterEntity) rangedAttackMob).entityData.set(SHOOT, false);
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
					((MegaStonecutterEntity) rangedAttackMob).entityData.set(SHOOT, false);
					return;
				}
				((MegaStonecutterEntity) rangedAttackMob).entityData.set(SHOOT, true);
				float f = (float) Math.sqrt(d0) / this.attackRadius;
				float f1 = Mth.clamp(f, 0.1F, 1.0F);
				this.rangedAttackMob.performRangedAttack(this.target, f1);
				this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
			} else if (this.attackTime < 0) {
				this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) this.attackRadius, (double) this.attackIntervalMin, (double) this.attackIntervalMax));
			} else
				((MegaStonecutterEntity) rangedAttackMob).entityData.set(SHOOT, false);
		}
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	public boolean canBeAffected(MobEffectInstance pEffectInstance){
		if (inSkill() && pEffectInstance.getEffect() != CaerulaArborModMobEffects.SUPER_DESTRUCTIVE_MODE.get()) return false;
		return super.canBeAffected(pEffectInstance);
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return SoundEvents.TURTLE_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.TURTLE_DEATH;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.IN_FIRE))
			return false;
		if (source.is(DamageTypes.FALL))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		if (source.is(DamageTypes.EXPLOSION))
			return false;
		if (source.is(DamageTypes.FALLING_ANVIL))
			return false;
		float scale = 1;
		if (source.getEntity() != null) 
			scale = GetEntityCosineProcedure.execute(this, source.getEntity()) > 0.5 ? 1: 0.5f;
		
		return super.hurt(source, amount * scale);
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		DropManyStonecutterProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ());
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		InitMegaStonecutterProcedure.execute(this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataSKILL_TICk", this.entityData.get(DATA_SKILL_TICk));
		compound.putInt("DataSKILL_COOLDOWN", this.entityData.get(DATA_SKILL_COOLDOWN));
		compound.putBoolean("DataSHOOTINGGG", this.entityData.get(DATA_SHOOTINGGG));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataSKILL_TICk"))
			this.entityData.set(DATA_SKILL_TICk, compound.getInt("DataSKILL_TICk"));
		if (compound.contains("DataSKILL_COOLDOWN"))
			this.entityData.set(DATA_SKILL_COOLDOWN, compound.getInt("DataSKILL_COOLDOWN"));
		if (compound.contains("DataSHOOTINGGG"))
			this.entityData.set(DATA_SHOOTINGGG, compound.getBoolean("DataSHOOTINGGG"));
	}

	@Override
	public void tick(){
		super.tick();
		if (!this.level().isClientSide()) MegaStoneCutterTicksProcedure.execute(this);
		int sklt = entityData.get(DATA_SKILL_TICk);
		if (sklt > 0 && entityData.get(DATA_SHOOTINGGG) && !this.isDeadOrDying()){
			LivingEntity enemy = this.getTarget();
			if (enemy != null && enemy.isAlive() && sklt % 2 == 1){
				FishShootEntity.shoot(this, enemy, true);
				List<LivingEntity> _entfound = this.level().getEntitiesOfClass(LivingEntity.class, 
						new AABB(this.position(), this.position()).inflate(24), 
						e -> e.isAlive() && e != this && e != enemy && !(e instanceof Player))
					.stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(this.position()))).toList();
				int count = 0;
				for (LivingEntity entityiterator : _entfound) {
					FishShootEntity.shoot(this, entityiterator, true);
					count ++;
					if (count >= 3) break;
				}
			}
		}
	}

	@Override
	public void baseTick() {
		super.baseTick();
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public void performRangedAttack(LivingEntity target, float flval) {
		if (inSkill()) return;
		CaerulaArborMod.queueServerWork(13, () -> {
			FishShootEntity.shoot(this, target, false);
		});
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
		builder = builder.add(Attributes.MAX_HEALTH, 1200);
		builder = builder.add(Attributes.ARMOR, 44);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 9);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 99);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
			return event.setAndContinue(RawAnimation.begin().thenPlay("die"));
		}
		if (entityData.get(DATA_SHOOTINGGG)){
			return event.setAndContinue(RawAnimation.begin().thenLoop("skill_loop"));
		}
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.1F && event.getLimbSwingAmount() < 0.1F))
			) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("move"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
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
		if (this.swinging && this.lastSwing + 20L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (!inSkill() && (this.swinging || this.entityData.get(SHOOT)) && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("attack"));
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

	public boolean inSkill(){
		return entityData.get(DATA_SKILL_TICk) > 0;
	}
}
