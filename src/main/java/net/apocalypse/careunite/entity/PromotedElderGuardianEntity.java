
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

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.IronGolem;
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
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.core.BlockPos;
import net.apocalypse.careunite.procedures.ElderGuardianTickProcedure;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModSounds;

import javax.annotation.Nullable;

import java.util.EnumSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;

public class PromotedElderGuardianEntity extends Monster implements RangedAttackMob, GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(PromotedElderGuardianEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(PromotedElderGuardianEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(PromotedElderGuardianEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_LOCK_TICK = SynchedEntityData.defineId(PromotedElderGuardianEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_DURATION = SynchedEntityData.defineId(PromotedElderGuardianEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_SKILL_P = SynchedEntityData.defineId(PromotedElderGuardianEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_TARGET_ID = SynchedEntityData.defineId(PromotedElderGuardianEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_EXTRA_EYE = SynchedEntityData.defineId(PromotedElderGuardianEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.BLUE, ServerBossEvent.BossBarOverlay.PROGRESS);

	public PromotedElderGuardianEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.PROMOTED_ELDER_GUARDIAN.get(), world);
	}

	public PromotedElderGuardianEntity(EntityType<PromotedElderGuardianEntity> type, Level world) {
		super(type, world);
		xpReward = 99;
		setNoAi(false);
		setMaxUpStep(0.6f);
		setPersistenceRequired();
		this.moveControl = new FlyingMoveControl(this, 10, true);
	}

	public Entity getClientTarget(){
		return this.level().getEntity(entityData.get(DATA_TARGET_ID));
	}
	public int getLockTick(){
		return this.entityData.get(DATA_LOCK_TICK);
	}
	public boolean shouldEyeBlink(int index){
		return clientEyeBlinkTick[index] > 0;
	}
	public int getExtraEye(){return entityData.get(DATA_EXTRA_EYE);}


	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "promoted_elder_guardian");
		this.entityData.define(DATA_LOCK_TICK, 0);
		this.entityData.define(DATA_DURATION, 0);
		this.entityData.define(DATA_SKILL_P, 120);
		this.entityData.define(DATA_TARGET_ID, 0);
		this.entityData.define(DATA_EXTRA_EYE, 0);
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public String getTexture() {
		return this.entityData.get(TEXTURE);
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 1F;
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1.125f);
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
		this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, IronGolem.class, true, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Squid.class, true, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, GlowSquid.class, true, false));
		this.targetSelector.addGoal(5, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(1, new PromotedElderGuardianEntity.RangedAttackGoal(this, 1.25, 60, 72f) {
			@Override
			public boolean canContinueToUse() {
				return this.canUse();
			}
		});
	}

	public class RangedAttackGoal extends Goal {
		private final PromotedElderGuardianEntity mob;
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

		public RangedAttackGoal(PromotedElderGuardianEntity p_25768_, double p_25769_, int p_25770_, float p_25771_) {
			this(p_25768_, p_25769_, p_25770_, p_25770_, p_25771_);
		}

		public RangedAttackGoal(PromotedElderGuardianEntity p_25773_, double p_25774_, int p_25775_, int p_25776_, float p_25777_) {
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
				return mob.getEntityData().get(DATA_DURATION) <= 0;
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
			((PromotedElderGuardianEntity) rangedAttackMob).entityData.set(SHOOT, false);
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
					((PromotedElderGuardianEntity) rangedAttackMob).entityData.set(SHOOT, false);
					return;
				}
				((PromotedElderGuardianEntity) rangedAttackMob).entityData.set(SHOOT, true);
				float f = (float) Math.sqrt(d0) / this.attackRadius;
				float f1 = Mth.clamp(f, 0.1F, 1.0F);
				this.rangedAttackMob.performRangedAttack(this.target, f1);
				this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
			} else if (this.attackTime < 0) {
				this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) this.attackRadius, (double) this.attackIntervalMin, (double) this.attackIntervalMax));
			} else
				((PromotedElderGuardianEntity) rangedAttackMob).entityData.set(SHOOT, false);
		}
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
		if (this.isInWater()) return CaerulaArborModSounds.SEABORN_MASSIVE_AMBIENT.get();
		return SoundEvents.ELDER_GUARDIAN_AMBIENT;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		if (this.isInWater()) return CaerulaArborModSounds.SEABORN_MASSIVE_HURT.get();
		return SoundEvents.ELDER_GUARDIAN_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		if (this.isInWater()) return CaerulaArborModSounds.SEABORN_MASSIVE_DEATH.get();
		return SoundEvents.ELDER_GUARDIAN_DEATH;
	}

	@Override
	public boolean causeFallDamage(float l, float d, DamageSource source) {
		return false;
	}

	private int lastCounterTick = -1;
	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.DROWN))
			return false;

		boolean flag = super.hurt(source, amount);
		if (!this.isDeadOrDying() && flag && this.tickCount > lastCounterTick){
			lastCounterTick = tickCount;
			Entity sourceEnt = source.getEntity();
			if (sourceEnt == this.getTarget()) amount *= 0.75f;
			if (sourceEnt != null && this.distanceToSqr(sourceEnt) <= 9){
				sourceEnt.hurt(this.level().damageSources().mobAttack(this),(float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
			}
		}
		return flag;

	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataLOCK_TICK", this.entityData.get(DATA_LOCK_TICK));
		compound.putInt("DataDURATION", this.entityData.get(DATA_DURATION));
		compound.putInt("DataSKILL_P", this.entityData.get(DATA_SKILL_P));
		compound.putInt("DataTARGET_ID", this.entityData.get(DATA_TARGET_ID));
		compound.putInt("DataEXTRA_EYE", this.entityData.get(DATA_EXTRA_EYE));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataLOCK_TICK"))
			this.entityData.set(DATA_LOCK_TICK, compound.getInt("DataLOCK_TICK"));
		if (compound.contains("DataDURATION"))
			this.entityData.set(DATA_DURATION, compound.getInt("DataDURATION"));
		if (compound.contains("DataSKILL_P"))
			this.entityData.set(DATA_SKILL_P, compound.getInt("DataSKILL_P"));
		if (compound.contains("DataTARGET_ID"))
			this.entityData.set(DATA_TARGET_ID, compound.getInt("DataTARGET_ID"));
		if (compound.contains("DataEXTRA_EYE"))
			this.entityData.set(DATA_EXTRA_EYE, compound.getInt("DataEXTRA_EYE"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		ElderGuardianTickProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	public final int[] clientEyeBlinkTick = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
	public final int[] clientEyeNextBlinkTick = new int[]{0, 0, 0, 0, 0, 0, 0, 0};

	@Override
	public void tick(){
		super.tick();
		if (this.level().isClientSide()){
			for (int i = 0; i< 8; i++){
				if (clientEyeBlinkTick[i] > 0) clientEyeBlinkTick[i] --;
				if (clientEyeNextBlinkTick[i] > 0) clientEyeNextBlinkTick[i] --;
				else {
					clientEyeNextBlinkTick[i] = Mth.nextInt(this.getRandom(), 20, 90);
					clientEyeBlinkTick[i] = 10;
				}
			}
		}
		LivingEntity target = this.getTarget();
		if (target != null && target.isAlive()){
			this.entityData.set(DATA_TARGET_ID, target.getId());
		}
	}

	@Override
	public void performRangedAttack(LivingEntity target, float flval) {
		this.entityData.set(DATA_LOCK_TICK, 40);
		this.entityData.set(DATA_EXTRA_EYE, Mth.nextInt(this.getRandom(), 0, 8));
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
		if (this.isDeadOrDying()) super.setNoGravity(ignored);
		else super.setNoGravity(true);
	}

	public void aiStep() {
		super.aiStep();
		if (!this.isDeadOrDying()) this.setNoGravity(true);
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 165);
		builder = builder.add(Attributes.ARMOR, 11);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 14);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		builder = builder.add(Attributes.FLYING_SPEED, 0.3);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
			return event.setAndContinue(RawAnimation.begin().thenPlay("die"));
		}
		if (this.animationprocedure.equals("empty")) {
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

	@Override
	protected void tickDeath() {
		++this.deathTime;
		this.setNoGravity(false);
		if (this.deathTime == 20) {
			this.remove(PromotedElderGuardianEntity.RemovalReason.KILLED);
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
		data.add(new AnimationController<>(this, "procedure", 1, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
