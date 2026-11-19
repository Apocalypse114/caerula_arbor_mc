
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
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.LumenSkillsProcedure;
import net.apocalypse.careunite.procedures.LumenPerformHealProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import javax.annotation.Nullable;

import java.util.EnumSet;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundEvents;

public class LumenEntity extends TamableAnimal implements RangedAttackMob, GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(LumenEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(LumenEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(LumenEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_SKILLP_1 = SynchedEntityData.defineId(LumenEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_SKILLP_2 = SynchedEntityData.defineId(LumenEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_BLINK_T = SynchedEntityData.defineId(LumenEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_DURATION = SynchedEntityData.defineId(LumenEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public LumenEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.LUMEN.get(), world);
	}

	public LumenEntity(EntityType<LumenEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
		setMaxUpStep(0.6f);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "lumen");
		this.entityData.define(DATA_SKILLP_1, 100);
		this.entityData.define(DATA_SKILLP_2, 180);
		this.entityData.define(DATA_BLINK_T, 0);
		this.entityData.define(DATA_DURATION, 0);
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
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Monster.class, false, false));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(5, new FloatGoal(this));
		this.goalSelector.addGoal(1, new LumenEntity.RangedAttackGoal(this, 1, 40, 9f) {
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
			((LumenEntity) rangedAttackMob).entityData.set(SHOOT, false);
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
					((LumenEntity) rangedAttackMob).entityData.set(SHOOT, false);
					return;
				}
				((LumenEntity) rangedAttackMob).entityData.set(SHOOT, true);
				float f = (float) Math.sqrt(d0) / this.attackRadius;
				float f1 = Mth.clamp(f, 0.1F, 1.0F);
				this.rangedAttackMob.performRangedAttack(this.target, f1);
				this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
			} else if (this.attackTime < 0) {
				this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) this.attackRadius, (double) this.attackIntervalMin, (double) this.attackIntervalMax));
			} else
				((LumenEntity) rangedAttackMob).entityData.set(SHOOT, false);
		}
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
		compound.putInt("DataSKILLP_1", this.entityData.get(DATA_SKILLP_1));
		compound.putInt("DataSKILLP_2", this.entityData.get(DATA_SKILLP_2));
		compound.putInt("DataBLINK_T", this.entityData.get(DATA_BLINK_T));
		compound.putInt("DataDURATION", this.entityData.get(DATA_DURATION));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataSKILLP_1"))
			this.entityData.set(DATA_SKILLP_1, compound.getInt("DataSKILLP_1"));
		if (compound.contains("DataSKILLP_2"))
			this.entityData.set(DATA_SKILLP_2, compound.getInt("DataSKILLP_2"));
		if (compound.contains("DataBLINK_T"))
			this.entityData.set(DATA_BLINK_T, compound.getInt("DataBLINK_T"));
		if (compound.contains("DataDURATION"))
			this.entityData.set(DATA_DURATION, compound.getInt("DataDURATION"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		LumenSkillsProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	public int timeTillNextBlink = 40;

	@Override
	public void tick(){
		super.tick();
		timeTillNextBlink--;
		if (timeTillNextBlink <= 0){
			this.entityData.set(DATA_BLINK_T, 6);
			timeTillNextBlink = Mth.nextInt(this.getRandom(), 40, 60);
		}
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public void performRangedAttack(LivingEntity target, float flval) {
		LumenPerformHealProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
	}

	public static void init() {
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
		return ageable;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return false;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.19);
		builder = builder.add(Attributes.MAX_HEALTH, 70);
		builder = builder.add(Attributes.ARMOR, 5);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.1F && event.getLimbSwingAmount() < 0.1F))

			) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("move"));
			}
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("die"));
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
		if ((this.swinging || this.entityData.get(SHOOT)) && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("heal"));
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

	public boolean shouldBlink(){
		return this.entityData.get(DATA_BLINK_T) > 0;
	}
}
