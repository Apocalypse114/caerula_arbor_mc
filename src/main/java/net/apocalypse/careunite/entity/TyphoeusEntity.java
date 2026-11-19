
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

import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.TypheousTicksProcedure;
import net.apocalypse.careunite.procedures.TameInquisitorProcedure;
import net.apocalypse.careunite.procedures.IsTypyoeusDurativeProcedure;
import net.apocalypse.careunite.procedures.IsValidEnemyForIreneProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModSounds;

import net.apocalypse.careunite.CaerulaArborMod;

import javax.annotation.Nullable;

import java.util.List;
import java.util.EnumSet;
import net.minecraft.sounds.SoundEvents;
import java.util.Comparator;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.sounds.SoundSource;

public class TyphoeusEntity extends TamableAnimal implements RangedAttackMob, GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(TyphoeusEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(TyphoeusEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(TyphoeusEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_DURATION = SynchedEntityData.defineId(TyphoeusEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_SKILL_T = SynchedEntityData.defineId(TyphoeusEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_SKILL_COOLDOWN_1 = SynchedEntityData.defineId(TyphoeusEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_SKILL_COOLDOWN_2 = SynchedEntityData.defineId(TyphoeusEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_AFLOAT_T = SynchedEntityData.defineId(TyphoeusEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_AFLOAT_SHOOT_LEFT = SynchedEntityData.defineId(TyphoeusEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public TyphoeusEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.TYPHOEUS.get(), world);
	}

	public TyphoeusEntity(EntityType<TyphoeusEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
		setMaxUpStep(0.8f);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "archer");
		this.entityData.define(DATA_DURATION, 0);
		this.entityData.define(DATA_SKILL_T, 0);
		this.entityData.define(DATA_SKILL_COOLDOWN_1, 100);
		this.entityData.define(DATA_SKILL_COOLDOWN_2, 180);
		this.entityData.define(DATA_AFLOAT_T, 0);
		this.entityData.define(DATA_AFLOAT_SHOOT_LEFT, 0);
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
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Monster.class, true, false));
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = TyphoeusEntity.this.getX();
				double y = TyphoeusEntity.this.getY();
				double z = TyphoeusEntity.this.getZ();
				Entity entity = TyphoeusEntity.this;
				Level world = TyphoeusEntity.this.level();
				return super.canUse() && IsTypyoeusDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = TyphoeusEntity.this.getX();
				double y = TyphoeusEntity.this.getY();
				double z = TyphoeusEntity.this.getZ();
				Entity entity = TyphoeusEntity.this;
				Level world = TyphoeusEntity.this.level();
				return super.canContinueToUse() && IsTypyoeusDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = TyphoeusEntity.this.getX();
				double y = TyphoeusEntity.this.getY();
				double z = TyphoeusEntity.this.getZ();
				Entity entity = TyphoeusEntity.this;
				Level world = TyphoeusEntity.this.level();
				return super.canUse() && IsTypyoeusDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = TyphoeusEntity.this.getX();
				double y = TyphoeusEntity.this.getY();
				double z = TyphoeusEntity.this.getZ();
				Entity entity = TyphoeusEntity.this;
				Level world = TyphoeusEntity.this.level();
				return super.canContinueToUse() && IsTypyoeusDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(5, new FloatGoal(this));
		this.goalSelector.addGoal(1, new TyphoeusEntity.RangedAttackGoal(this, 1, 40, 24f) {
			@Override
			public boolean canUse(){
				return super.canUse() && !TyphoeusEntity.this.afloat();
			}
			@Override
			public boolean canContinueToUse() {
				return this.canUse();
			}
		});
		this.goalSelector.addGoal(1, new TyphoeusEntity.RangedAttackGoal(this, 1, 20, 24f) {
			@Override
			public boolean canUse(){
				return super.canUse() && TyphoeusEntity.this.afloat();
			}
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
				return IsTypyoeusDurativeProcedure.execute(this.mob);
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
			((TyphoeusEntity) rangedAttackMob).entityData.set(SHOOT, false);
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
					((TyphoeusEntity) rangedAttackMob).entityData.set(SHOOT, false);
					return;
				}
				((TyphoeusEntity) rangedAttackMob).entityData.set(SHOOT, true);
				float f = (float) Math.sqrt(d0) / this.attackRadius;
				float f1 = Mth.clamp(f, 0.1F, 1.0F);
				this.rangedAttackMob.performRangedAttack(this.target, f1);
				this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
			} else if (this.attackTime < 0) {
				this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) this.attackRadius, (double) this.attackIntervalMin, (double) this.attackIntervalMax));
			} else
				((TyphoeusEntity) rangedAttackMob).entityData.set(SHOOT, false);
		}
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	public float getStandingEyeHeight(Pose pPose,EntityDimensions pDimensions){
		return super.getStandingEyeHeight(pPose, pDimensions) + (afloat() ? 1.5f: 0);
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return SoundEvents.GENERIC_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.GENERIC_DEATH;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataDURATION", this.entityData.get(DATA_DURATION));
		compound.putInt("DataSKILL_T", this.entityData.get(DATA_SKILL_T));
		compound.putInt("DataSKILL_COOLDOWN_1", this.entityData.get(DATA_SKILL_COOLDOWN_1));
		compound.putInt("DataSKILL_COOLDOWN_2", this.entityData.get(DATA_SKILL_COOLDOWN_2));
		compound.putInt("DataAFLOAT_T", this.entityData.get(DATA_AFLOAT_T));
		compound.putInt("DataAFLOAT_SHOOT_LEFT", this.entityData.get(DATA_AFLOAT_SHOOT_LEFT));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataDURATION"))
			this.entityData.set(DATA_DURATION, compound.getInt("DataDURATION"));
		if (compound.contains("DataSKILL_T"))
			this.entityData.set(DATA_SKILL_T, compound.getInt("DataSKILL_T"));
		if (compound.contains("DataSKILL_COOLDOWN_1"))
			this.entityData.set(DATA_SKILL_COOLDOWN_1, compound.getInt("DataSKILL_COOLDOWN_1"));
		if (compound.contains("DataSKILL_COOLDOWN_2"))
			this.entityData.set(DATA_SKILL_COOLDOWN_2, compound.getInt("DataSKILL_COOLDOWN_2"));
		if (compound.contains("DataAFLOAT_T"))
			this.entityData.set(DATA_AFLOAT_T, compound.getInt("DataAFLOAT_T"));
		if (compound.contains("DataAFLOAT_SHOOT_LEFT"))
			this.entityData.set(DATA_AFLOAT_SHOOT_LEFT, compound.getInt("DataAFLOAT_SHOOT_LEFT"));
	}

	@Override
	public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();
		Entity entity = this;
		Level world = this.level();
		return TameInquisitorProcedure.execute(world, x, y, z, entity, sourceentity);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		TypheousTicksProcedure.execute(this.level(),this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public void performRangedAttack(LivingEntity target, float flval) {
		int t = this.entityData.get(DATA_AFLOAT_SHOOT_LEFT);
		final boolean isInFloatSkill = t <= 0 ? false: afloat();
		if (t > 0) this.entityData.set(DATA_AFLOAT_SHOOT_LEFT, t - 1);
		final int shootDelay = isInFloatSkill ? 1: 12;
		//final int pullDelay = isInFloatSkill ? 12: 16;
		CaerulaArborMod.queueServerWork(shootDelay, ()->{
				doShoot(target, isInFloatSkill);
			}
		);
//		CaerulaArborMod.queueServerWork(pullDelay, ()->{
//			if (!this.isDeadOrDying())
//				this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
//					SoundEvents.CROSSBOW_QUICK_CHARGE_1, SoundSource.NEUTRAL, 1.0F, this.getRandom().nextFloat() * 0.5f + 0.75f);
//				}
//		);
	}

	private void doShoot(LivingEntity target, boolean inSkill){
		if (this.isDeadOrDying()) return;
		if (inSkill) doSkillShoot(target);
		else LargeArrowEntity.shoot(this, target);
		this.level().playSound(null, this.getX(), this.getY(), this.getZ(), 
			inSkill ? CaerulaArborModSounds.TYPHEOUS_AFLOAT_SHOOT.get(): CaerulaArborModSounds.TYPHEOUS_SHOOT.get(), 
			SoundSource.NEUTRAL, 1, 1f / (this.getRandom().nextFloat() * 0.5f + 1));

	}

	private void doSkillShoot(LivingEntity target){
		LargeArrowEntity.shoot(this, target, true);
		final Vec3 _center = target.position();
		List<LivingEntity> _entfound = this.level().getEntitiesOfClass(LivingEntity.class, 
			new AABB(_center, _center).inflate(8), e -> e != target && IsValidEnemyForIreneProcedure.execute(e, this))
				.stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
		int count = 0;
		for (LivingEntity entityiterator : _entfound) {
			LargeArrowEntity.shoot(this, entityiterator, true);
			count ++;
			if (count >= 4) break;
		}
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
		TyphoeusEntity retval = CaerulaArborModEntities.TYPHOEUS.get().create(serverWorld);
		retval.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(retval.blockPosition()), MobSpawnType.BREEDING, null, null);
		return retval;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return false;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.updateSwingTime();
	}

	public static void init() {
	}

	@Override
	public boolean hurt(DamageSource pSource,float pAmount){
		if (inSkill()) pAmount *= 0.02;
		return super.hurt(pSource, pAmount);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 76);
		builder = builder.add(Attributes.ARMOR, 3);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 19);
		builder = builder.add(Attributes.FOLLOW_RANGE, 36);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.85);
		return builder;
	}

	@Override
    public boolean isPushable() {
        return !afloat();
    }
    @Override
    protected void doPush(Entity entityIn) {
    	if (afloat()) return;
    	super.doPush(entityIn);
    }
    @Override
    protected void pushEntities() {
    	if (afloat()) return;
    	super.pushEntities();
    }

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
			return event.setAndContinue(RawAnimation.begin().thenPlay("die"));
		}
		if (this.animationprocedure.equals("empty")) {
			if (inSkill()) return event.setAndContinue(RawAnimation.begin().thenLoop("skill_shoot"));
			if (afloat()) return event.setAndContinue(RawAnimation.begin().thenLoop("afloat"));
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.1F && event.getLimbSwingAmount() < 0.1F)))
 {
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
		if (this.swinging && this.lastSwing + 23L <= level().getGameTime()) {
			this.swinging = false;
		}
		if ((this.swinging || this.entityData.get(SHOOT)) && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			if (afloat()) return event.setAndContinue(RawAnimation.begin().thenPlay("afloat_attack"));
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
	public boolean afloat(){
		return entityData.get(DATA_AFLOAT_T) > 0;
	}
	public boolean inSkill(){
		return entityData.get(DATA_SKILL_T) > 0;
	}
	public int tickInSkill(){
		return 40 - entityData.get(DATA_SKILL_T);
	}
}
