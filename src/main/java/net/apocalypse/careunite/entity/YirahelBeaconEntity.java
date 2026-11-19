
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.AlchemyCreation;
import net.apocalypse.careunite.init.CaerulaArborModSounds;
import net.apocalypse.careunite.CaerulaArborMod;
import net.apocalypse.careunite.procedures.OnBeaconTickProcedure;
import net.apocalypse.careunite.init.CaerulaArborModItems;

import javax.annotation.Nullable;

import java.util.EnumSet;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

public class YirahelBeaconEntity extends AlchemyCreation implements RangedAttackMob, GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(YirahelBeaconEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(YirahelBeaconEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(YirahelBeaconEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_ATTACK_ANIM = SynchedEntityData.defineId(YirahelBeaconEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_LASER_T = SynchedEntityData.defineId(YirahelBeaconEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_SKILL_T = SynchedEntityData.defineId(YirahelBeaconEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_LASER_COOLDOWN = SynchedEntityData.defineId(YirahelBeaconEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_SKILL_COOLDOWN = SynchedEntityData.defineId(YirahelBeaconEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> BASE = SynchedEntityData.defineId(YirahelBeaconEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> INV_TIME = SynchedEntityData.defineId(YirahelBeaconEntity.class, EntityDataSerializers.INT);

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.BLUE, ServerBossEvent.BossBarOverlay.PROGRESS);

	public YirahelBeaconEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.YIRAHEL_BEACON.get(), world);
	}

	public YirahelBeaconEntity(EntityType<YirahelBeaconEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
		setMaxUpStep(1f);
		setPersistenceRequired();
		this.moveControl = new FlyingMoveControl(this, 10, true);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "yirahel_beacon");
		this.entityData.define(DATA_ATTACK_ANIM, 0);
		this.entityData.define(DATA_LASER_T, 0);
		this.entityData.define(DATA_SKILL_T, 0);
		this.entityData.define(DATA_LASER_COOLDOWN, 160);
		this.entityData.define(DATA_SKILL_COOLDOWN, 280);
		this.entityData.define(BASE, 1);
		this.entityData.define(INV_TIME, 0);
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public String getTexture() {
		return this.entityData.get(TEXTURE);
	}
	
	public int extractionCooldown(){
    	return 160;
    }

    public int injectionType(){
    	return 2;
    }

    public boolean canOverflow(){
		return true;
	}

	public int maxBase(){
		return 4;
	}

	public boolean canInject(){
		if (inLaserSkill() || inDomainSkill()) return false;
    	return super.canInject();
    }

    public boolean shouldTryAbsorb(){
    	return false;
    }

    public void lostBase(int count, boolean release){
    	if (inLaserSkill() || inDomainSkill()) return;
    	super.lostBase(count, release);
    }

    public void gainBase(int count){
    	if (inLaserSkill() || inDomainSkill()) return;
    	super.gainBase(count);
    }

    public void setStunned(boolean st){
    	boolean prevSt = isStunned();
		if (!prevSt && st) this.setAnimation("drop");
		else if (prevSt && !st) this.setAnimation("revive");
		this.setNoGravity(!st);
		super.setStunned(st);
	}

	public void spawnedFromInjection(BlockState state, Player pPlayer){
		ItemStack stack = pPlayer.getItemInHand(InteractionHand.OFF_HAND);
		this.level().playSound(null, this.getX(), this.getY(), this.getZ(), CaerulaArborModSounds.BEACON_SPAWN.get(), SoundSource.NEUTRAL, 1,
			1f / (this.getRandom().nextFloat() * 0.5f + 1));
		if (!stack.isEmpty() && stack.is(CaerulaArborModItems.BEACON_CRYSTAL.get())){
			stack.hurtAndBreak(1, pPlayer, e -> {});
			super.spawnedFromInjection(state, pPlayer);
		} else {
			pPlayer.displayClientMessage(Component.translatable("entity.caerula_arbor_reunite.yirahel_beacon.note"), false);
			pPlayer.displayClientMessage(Component.translatable("entity.caerula_arbor_reunite.yirahel_beacon.note2"), false);
		}
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
		this.goalSelector.addGoal(1, new OwnerHurtByTargetGoal(this){
			@Override
			public boolean canUse() {
				YirahelBeaconEntity entity = YirahelBeaconEntity.this;
				return super.canUse() && entity.canAction();
			}
			@Override
			public boolean canContinueToUse() {
				YirahelBeaconEntity entity = YirahelBeaconEntity.this;
				return super.canContinueToUse() && entity.canAction();
			}
		});
		this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this){
			@Override
			public boolean canUse() {
				YirahelBeaconEntity entity = YirahelBeaconEntity.this;
				return super.canUse() && entity.canAction();
			}
			@Override
			public boolean canContinueToUse() {
				YirahelBeaconEntity entity = YirahelBeaconEntity.this;
				return super.canContinueToUse() && entity.canAction();
			}
		});
		this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1, (float) 4, (float) 18, false){
			@Override
			public boolean canUse() {
				YirahelBeaconEntity entity = YirahelBeaconEntity.this;
				return super.canUse() && entity.canAction();
			}
			@Override
			public boolean canContinueToUse() {
				YirahelBeaconEntity entity = YirahelBeaconEntity.this;
				return super.canContinueToUse() && entity.canAction();
			}
		});
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1){
			@Override
			public boolean canUse() {
				YirahelBeaconEntity entity = YirahelBeaconEntity.this;
				return super.canUse() && entity.canAction();
			}
			@Override
			public boolean canContinueToUse() {
				YirahelBeaconEntity entity = YirahelBeaconEntity.this;
				return super.canContinueToUse() && entity.canAction();
			}
		});
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this){
			@Override
			public boolean canUse() {
				YirahelBeaconEntity entity = YirahelBeaconEntity.this;
				return super.canUse() && entity.canAction();
			}
			@Override
			public boolean canContinueToUse() {
				YirahelBeaconEntity entity = YirahelBeaconEntity.this;
				return super.canContinueToUse() && entity.canAction();
			}
		});
		this.goalSelector.addGoal(1, new YirahelBeaconEntity.RangedAttackGoal(this, 1.25, 60, 14f) {
			@Override
			public boolean canUse() {
				YirahelBeaconEntity entity = YirahelBeaconEntity.this;
				return super.canUse() && entity.canAction();
			}
			@Override
			public boolean canContinueToUse() {
				return this.canUse();
			}
		});
	}

	public boolean inLaserSkill(){
		return entityData.get(DATA_LASER_T) > 0;
	}
	public boolean inDomainSkill(){
		return entityData.get(DATA_SKILL_T) > 0;
	}

	public boolean canAction(){
		return !inLaserSkill() && !inDomainSkill() && !isStunned() && !sitting;
	}

	public boolean sitting = false;

	@Override
	public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
		if (sourceentity.isShiftKeyDown() && sourceentity == this.getOwner()){
			sitting = !sitting;
			return InteractionResult.SUCCESS;
		}
		return super.mobInteract(sourceentity, hand);
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
			((YirahelBeaconEntity) rangedAttackMob).entityData.set(SHOOT, false);
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
					((YirahelBeaconEntity) rangedAttackMob).entityData.set(SHOOT, false);
					return;
				}
				((YirahelBeaconEntity) rangedAttackMob).entityData.set(SHOOT, true);
				float f = (float) Math.sqrt(d0) / this.attackRadius;
				float f1 = Mth.clamp(f, 0.1F, 1.0F);
				this.rangedAttackMob.performRangedAttack(this.target, f1);
				this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
			} else if (this.attackTime < 0) {
				this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) this.attackRadius, (double) this.attackIntervalMin, (double) this.attackIntervalMax));
			} else
				((YirahelBeaconEntity) rangedAttackMob).entityData.set(SHOOT, false);
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
	public boolean causeFallDamage(float l, float d, DamageSource source) {
		return false;
	}

	public int getInvTime(){
		return entityData.get(INV_TIME);
	}

	public void setInvtime(int t){
		entityData.set(INV_TIME, t);
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.FALL))
			return false;
		return super.hurt(source, amount);
	}

	public void actuallyHurt(DamageSource source, float amount){
		if (isStunned()){
			super.actuallyHurt(source, amount);
			return;
		}
		int b = Mth.clamp(getBaseHeld(), 0, 4);
		float cap = 1;
		float maxH = this.getMaxHealth();
		int curInvTime = getInvTime();
		if (b > 0){
			cap = 1 - b * 0.24f;
			if (curInvTime <= 0) setInvtime(b * 8);
		}
		amount = Math.min(amount, cap * maxH);
		if (curInvTime > 0) amount = Math.min(amount, 1);
		sitting = false;
		super.actuallyHurt(source, amount);
	}

	public void setHealth(float pHealth){
		int b = getBaseHeld();
		if (b > 0 && pHealth <= 0){
			super.setHealth(this.getMaxHealth());
			lostBase(1, false);
			if (!this.level().isClientSide()) setStunned(false);
			level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.TOTEM_USE, SoundSource.NEUTRAL, 1,
				1f / (this.getRandom().nextFloat() * 0.5f + 1));
			if (this.level() instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, this.getX(), this.getY() + 0.8, this.getZ(), 
					32, 0.8, 0.8, 0.8, 0.1);
			this.entityData.set(DATA_SKILL_COOLDOWN, 0);
			return;
		}
		super.setHealth(pHealth);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataATTACK_ANIM", this.entityData.get(DATA_ATTACK_ANIM));
		compound.putInt("DataLASER_T", this.entityData.get(DATA_LASER_T));
		compound.putInt("DataSKILL_T", this.entityData.get(DATA_SKILL_T));
		compound.putInt("DataLASER_COOLDOWN", this.entityData.get(DATA_LASER_COOLDOWN));
		compound.putInt("DataSKILL_COOLDOWN", this.entityData.get(DATA_SKILL_COOLDOWN));
		compound.putInt("invTime", getInvTime());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataATTACK_ANIM"))
			this.entityData.set(DATA_ATTACK_ANIM, compound.getInt("DataATTACK_ANIM"));
		if (compound.contains("DataLASER_T"))
			this.entityData.set(DATA_LASER_T, compound.getInt("DataLASER_T"));
		if (compound.contains("DataSKILL_T"))
			this.entityData.set(DATA_SKILL_T, compound.getInt("DataSKILL_T"));
		if (compound.contains("DataLASER_COOLDOWN"))
			this.entityData.set(DATA_LASER_COOLDOWN, compound.getInt("DataLASER_COOLDOWN"));
		if (compound.contains("DataSKILL_COOLDOWN"))
			this.entityData.set(DATA_SKILL_COOLDOWN, compound.getInt("DataSKILL_COOLDOWN"));
		setInvtime(compound.getInt("invTime"));
	}

	public int getBshd(){
		return Mth.clamp(entityData.get(BASE), 0, 4);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		if (!isStunned()) OnBeaconTickProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		else this.entityData.set(DATA_ATTACK_ANIM, 0);
		int vt = getInvTime();
		if (vt > 0) setInvtime(vt - 1);
		if (!this.level().isClientSide()) this.entityData.set(BASE, getBaseHeld());
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public void performRangedAttack(LivingEntity target, float flval) {
		this.entityData.set(DATA_ATTACK_ANIM, 40);
		final int t = this.getBaseHeld();
		if (t >= 0){
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), CaerulaArborModSounds.BEACON_ATTACK_PRE.get(), SoundSource.NEUTRAL, 1,
				1f / (this.getRandom().nextFloat() * 0.5f + 1));
			Vec3 p = this.getLookAngle();
			this.push(p.x * -0.5, p.y * -0.5, p.z * -0.5);
			CaerulaArborMod.queueServerWork(28, ()->{
				if (!this.isStunned() && !this.isDeadOrDying()) TellerShotEntity.shoot(this, target, t + 1);
				Vec3 p1 = this.getLookAngle();
				this.push(p1.x * 0.4, p1.y * 0.4, p1.z * 0.4);
				}
			);
		}
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
	public void aiStep() {
		super.aiStep();
		this.updateSwingTime();
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.26);
		builder = builder.add(Attributes.MAX_HEALTH, 96);
		builder = builder.add(Attributes.ARMOR, 8);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 9);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 10);
		builder = builder.add(Attributes.FLYING_SPEED, 0.26);
		return builder;
	}

	public boolean renderLaser(){
		if (isStunned()) return false;
		return inLaserSkill() || inDomainSkill();
	}

	public boolean renderBullet(){
		return entityData.get(SHOOT);
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()){
			if (this.isStunned()) return event.setAndContinue(RawAnimation.begin().thenPlay("die_stun"));
			return event.setAndContinue(RawAnimation.begin().thenPlay("die"));
		}
		if (this.animationprocedure.equals("empty")) {
			if (this.isStunned()) return event.setAndContinue(RawAnimation.begin().thenLoop("overflow"));
			if (this.inLaserSkill()) return event.setAndContinue(RawAnimation.begin().thenPlay("laser"));
			if (this.inDomainSkill()) return event.setAndContinue(RawAnimation.begin().thenLoop("skill_loop"));
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
		if (this.swinging && this.lastSwing + 40L <= level().getGameTime()) {
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

	private PlayState rims(AnimationState event){
		return event.setAndContinue(RawAnimation.begin().thenLoop("test"));
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar data) {
		data.add(new AnimationController<>(this, "movement", 1, this::movementPredicate));
		data.add(new AnimationController<>(this, "attacking", 1, this::attackingPredicate));
		data.add(new AnimationController<>(this, "procedure", 1, this::procedurePredicate));
		data.add(new AnimationController<>(this, "rim", 0, this::rims));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
