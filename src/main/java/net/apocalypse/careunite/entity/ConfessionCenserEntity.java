
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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.util.Mth;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.InitCnesorMagicProcedure;
import net.apocalypse.careunite.procedures.CenserTicksProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.AlchemyCreation;
import net.apocalypse.careunite.init.CaerulaArborModSounds;
import net.apocalypse.careunite.CaerulaArborMod;

import javax.annotation.Nullable;

import java.util.EnumSet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.resources.ResourceLocation;

public class ConfessionCenserEntity extends AlchemyCreation implements RangedAttackMob, GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(ConfessionCenserEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(ConfessionCenserEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(ConfessionCenserEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_SKILL_P = SynchedEntityData.defineId(ConfessionCenserEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public boolean reversed;

	private static final ResourceLocation TP = new ResourceLocation("caerula_arbor_reunite", "textures/entities/cofession_censer.png");
	private static final ResourceLocation T0 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/cofession_censer_0.png");
	private static final ResourceLocation T1 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/cofession_censer_1.png");
	private static final ResourceLocation T2 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/cofession_censer_2.png");

	private static final ResourceLocation LP = new ResourceLocation("caerula_arbor_reunite", "textures/entities/censer_lit.png");
	private static final ResourceLocation L0 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/censer_lit_0.png");
	private static final ResourceLocation L1 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/censer_lit_1.png");
	private static final ResourceLocation L2 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/censer_lit_2.png");

	public ConfessionCenserEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.CONFESSION_CENSER.get(), world);
	}

	public ConfessionCenserEntity(EntityType<ConfessionCenserEntity> type, Level world) {
		super(type, world);
		xpReward = 6;
		setNoAi(false);
		setMaxUpStep(0.6f);
		setPersistenceRequired();
		this.reversed = world.getRandom().nextFloat() < 0.5f;
		setBaseHeld(4);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "2");
		this.entityData.define(DATA_SKILL_P, 0);
	}

	public int extractionCooldown(){
    	return 120;
    }

    public int injectionType(){
    	return 1;
    }

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public ResourceLocation getGlowmask(){
		String t = this.getTexture();
		if (t == "p") return LP;
		if (t == "0") return L0;
		if (t == "1") return L1;
		return L2;
	}

	public ResourceLocation getTextureRes(){
		String t = this.getTexture();
		if (t == "p") return TP;
		if (t == "0") return T0;
		if (t == "1") return T1;
		return T2;
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
		this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(3, new RandomLookAroundGoal(this){
			@Override
			public boolean canUse(){
				return super.canUse() && !ConfessionCenserEntity.this.isStunned();
			}
		});
		this.goalSelector.addGoal(4, new FloatGoal(this));
		this.goalSelector.addGoal(1, new ConfessionCenserEntity.RangedAttackGoal(this, 1.25, 60, 12f) {
			@Override
			public boolean canUse(){
				return super.canUse() && !ConfessionCenserEntity.this.isStunned() && !ConfessionCenserEntity.this.inSkill();
			}
			@Override
			public boolean canContinueToUse() {
				return this.canUse();
			}
		});
	}

	public boolean inSkill(){
		return !isStunned() && entityData.get(DATA_SKILL_P) > 0;
	}

	public void setStunned(boolean st){
		if (st) this.setAnimation("drop");
		else this.setAnimation("revive");
		super.setStunned(st);
	}

	public void lostBase(int count, boolean r){
		super.lostBase(count, r);
		int b = getBaseHeld();
		if (isStunned()) this.setTexture("p");
		else {
			if (b <= 0) this.setTexture("0");
			else if (b <= 3) this.setTexture("1");
			else this.setTexture("2");
		}
	}
	public void gainBase(int count){
		super.gainBase(count);
		int b = getBaseHeld();
		if (isStunned()) this.setTexture("p");
		else {
			if (b <= 0) this.setTexture("0");
			else if (b <= 3) this.setTexture("1");
			else this.setTexture("2");
		}
	}

	public int maxBase(){
		return 5;
	}

	public boolean canOverflow(){
		return true;
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
			((ConfessionCenserEntity) rangedAttackMob).entityData.set(SHOOT, false);
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
					((ConfessionCenserEntity) rangedAttackMob).entityData.set(SHOOT, false);
					return;
				}
				((ConfessionCenserEntity) rangedAttackMob).entityData.set(SHOOT, true);
				float f = (float) Math.sqrt(d0) / this.attackRadius;
				float f1 = Mth.clamp(f, 0.1F, 1.0F);
				this.rangedAttackMob.performRangedAttack(this.target, f1);
				this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
			} else if (this.attackTime < 0) {
				this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) this.attackRadius, (double) this.attackIntervalMin, (double) this.attackIntervalMax));
			} else
				((ConfessionCenserEntity) rangedAttackMob).entityData.set(SHOOT, false);
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
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		InitCnesorMagicProcedure.execute(this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataSKILL_P", this.entityData.get(DATA_SKILL_P));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataSKILL_P"))
			this.entityData.set(DATA_SKILL_P, compound.getInt("DataSKILL_P"));
		this.gainBase(0);

	}

	@Override
	public void baseTick() {
		super.baseTick();
		if (!isStunned()) CenserTicksProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public void performRangedAttack(LivingEntity target, float flval) {
		CaerulaArborMod.queueServerWork(19, ()->{
			if (!this.isStunned() && !this.isDeadOrDying()) TellerShotEntity.shoot(this, target);
			}
		);
	}

	public static void init() {
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.DROWN))
			return false;
		if (inSkill()) amount *= 0.6f;
		return super.hurt(source, amount);
	}


	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.16);
		builder = builder.add(Attributes.MAX_HEALTH, 48);
		builder = builder.add(Attributes.ARMOR, 5);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 4);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
			return event.setAndContinue(RawAnimation.begin().thenPlay("die"));
		}
		if (this.animationprocedure.equals("empty")) {
			if (this.isStunned()) return event.setAndContinue(RawAnimation.begin().thenLoop("overflow"));
			if (this.entityData.get(DATA_SKILL_P) > 0) return event.setAndContinue(RawAnimation.begin().thenLoop("skill"));
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
		if (this.swinging && this.lastSwing + 30L <= level().getGameTime()) {
			this.swinging = false;
		}
		if ((this.swinging || this.entityData.get(SHOOT)) && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
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
}
