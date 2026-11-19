
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.WanderingTradingProcedure;
import net.apocalypse.careunite.procedures.WanderingTicksProcedure;
import net.apocalypse.careunite.procedures.WanderingSummonLlamaProcedure;
import net.apocalypse.careunite.procedures.WanderingNotActioningProcedure;
import net.apocalypse.careunite.procedures.WanderingAddPhaseProcedure;
import net.apocalypse.careunite.procedures.CauseSanityProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModItems;


import net.apocalypse.careunite.CaerulaArborMod;
import javax.annotation.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.ArrayList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;
import net.minecraft.sounds.SoundSource;
import java.util.UUID;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.Difficulty;

public class OceanizedWanderingTraderEntity extends Monster implements RangedAttackMob, GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(OceanizedWanderingTraderEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(OceanizedWanderingTraderEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(OceanizedWanderingTraderEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_CURRENT_ACTION = SynchedEntityData.defineId(OceanizedWanderingTraderEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_ACTION_COOLDOWN = SynchedEntityData.defineId(OceanizedWanderingTraderEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_ACTION_TICK = SynchedEntityData.defineId(OceanizedWanderingTraderEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_INVISIBLE_TICK = SynchedEntityData.defineId(OceanizedWanderingTraderEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_TRADE_LEFT = SynchedEntityData.defineId(OceanizedWanderingTraderEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_SHOW_BOARD = SynchedEntityData.defineId(OceanizedWanderingTraderEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_PHASE = SynchedEntityData.defineId(OceanizedWanderingTraderEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_TRADE_TICK = SynchedEntityData.defineId(OceanizedWanderingTraderEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public final List<LivingEntity> myLlama = new ArrayList<>();

	public void putLlama(LivingEntity e){
		myLlama.add(e);
		if (e instanceof OceanizedLlamaEntity ll) ll.fromTrader = true;
	}
	public LivingEntity getLlama(){
		if (myLlama.isEmpty()) return myLlama.get(0);
		return null;
	}

	protected boolean shouldDespawnInPeaceful() {
      return false;
   	}
	
	public void tickLlama(){
		if (this.level() instanceof ServerLevel s && llamaUnchecked){
			if (uuid0 != null){
				Entity e0 = s.getEntity(uuid0);
				if (e0 instanceof LivingEntity l) myLlama.add(l);
			}
			if (uuid1 != null){
				Entity e1 = s.getEntity(uuid1);
				if (e1 instanceof LivingEntity l) myLlama.add(l);
			}
			llamaUnchecked = false;
		}
		LivingEntity activetarget = this.getTarget();
		boolean validtarget = activetarget != null && activetarget.isAlive();
		int good = this.entityData.get(DATA_TRADE_LEFT);
		if (!this.level().isClientSide()){
			if (validtarget && tickDespawn < 56000) tickDespawn ++;
			if (good <= 0 && !validtarget) tickDespawn --;
			if (tickDespawn <= 0) this.discard();
		}
		for (LivingEntity l: new ArrayList<>(myLlama)){
			if (l == null || l.isRemoved() || l.isDeadOrDying()){
				myLlama.remove(l);
			} else {
				if (this.isRemoved()){
					l.discard();
				}
				if (this.distanceToSqr(l) >= 2304){
					l.teleportTo(this.getX(), this.getY(), this.getZ());
				}
				if (l instanceof Mob m && validtarget){
					m.setTarget(this.getTarget());
				}
			}
		}
	}
	public boolean canSummonLLama(){
		return myLlama.isEmpty();
	}
	public LivingEntity lightLlama(){
		for (LivingEntity ll: myLlama){
			if (ll instanceof OceanizedLlamaEntity lla && !lla.isCharged()) return ll;
		}
		return null;
	}

	public OceanizedWanderingTraderEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.OCEANIZED_WANDERING_TRADER.get(), world);
	}

	public OceanizedWanderingTraderEntity(EntityType<OceanizedWanderingTraderEntity> type, Level world) {
		super(type, world);
		xpReward = 7;
		setNoAi(false);
		setMaxUpStep(0.6f);
		setPersistenceRequired();
	}

	private int tickDespawn = 56000;

	public void tick(){
		super.tick();
		tickLlama();
		
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "wandering_trader");
		this.entityData.define(DATA_CURRENT_ACTION, -1);
		this.entityData.define(DATA_ACTION_COOLDOWN, 0);
		this.entityData.define(DATA_ACTION_TICK, 0);
		this.entityData.define(DATA_INVISIBLE_TICK, 0);
		this.entityData.define(DATA_TRADE_LEFT, 4);
		this.entityData.define(DATA_SHOW_BOARD, false);
		this.entityData.define(DATA_PHASE, 0);
		this.entityData.define(DATA_TRADE_TICK, 0);
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
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.8, false) {
			@Override
			public boolean canUse(){
				return OceanizedWanderingTraderEntity.this.phase() > 0 
					&& OceanizedWanderingTraderEntity.this.level().getDifficulty() != Difficulty.PEACEFUL
					&& WanderingNotActioningProcedure.execute(OceanizedWanderingTraderEntity.this)
					&& super.canUse();
			}
			@Override
			public boolean canContinueToUse(){
				return OceanizedWanderingTraderEntity.this.phase() > 0 
					&& OceanizedWanderingTraderEntity.this.level().getDifficulty() != Difficulty.PEACEFUL
					&& WanderingNotActioningProcedure.execute(OceanizedWanderingTraderEntity.this)
					&& super.canContinueToUse();
			}
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 3.24;
			}
		});
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				Entity entity = OceanizedWanderingTraderEntity.this;
				return super.canUse() && WanderingNotActioningProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				Entity entity = OceanizedWanderingTraderEntity.this;
				return super.canUse() && WanderingNotActioningProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(5, new FloatGoal(this));
		this.goalSelector.addGoal(1, new OceanizedWanderingTraderEntity.RangedAttackGoal(this, 1.25, 50, 10f) {
			@Override
			public boolean canContinueToUse() {
				return this.canUse();
			}
		});
	}

	public class RangedAttackGoal extends Goal {
		private final OceanizedWanderingTraderEntity mob;
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

		public RangedAttackGoal(OceanizedWanderingTraderEntity p_25768_, double p_25769_, int p_25770_, float p_25771_) {
			this(p_25768_, p_25769_, p_25770_, p_25770_, p_25771_);
		}

		public RangedAttackGoal(OceanizedWanderingTraderEntity p_25773_, double p_25774_, int p_25775_, int p_25776_, float p_25777_) {
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
			if (this.mob.level().getDifficulty() == Difficulty.PEACEFUL) return false;
			LivingEntity livingentity = this.mob.getTarget();
			if (livingentity != null && livingentity.isAlive()) {
				this.target = livingentity;
				return this.mob.phase() < 1 && WanderingNotActioningProcedure.execute(this.mob);
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
			this.mob.entityData.set(SHOOT, false);
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
					((OceanizedWanderingTraderEntity) rangedAttackMob).entityData.set(SHOOT, false);
					return;
				}
				((OceanizedWanderingTraderEntity) rangedAttackMob).entityData.set(SHOOT, true);
				float f = (float) Math.sqrt(d0) / this.attackRadius;
				float f1 = Mth.clamp(f, 0.1F, 1.0F);
				this.rangedAttackMob.performRangedAttack(this.target, f1);
				this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
			} else if (this.attackTime < 0) {
				this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) this.attackRadius, (double) this.attackIntervalMin, (double) this.attackIntervalMax));
			} else
				((OceanizedWanderingTraderEntity) rangedAttackMob).entityData.set(SHOOT, false);
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
	public SoundEvent getAmbientSound() {
		return SoundEvents.WANDERING_TRADER_AMBIENT;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return SoundEvents.WANDERING_TRADER_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.WANDERING_TRADER_DEATH;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		
		if (source.is(DamageTypes.DROWN))
			return false;
		return super.hurt(source, amount);
	}

	@Override
    protected void dropCustomDeathLoot(DamageSource p_21018_, int p_21019_, boolean p_21020_) {
    	int b = entityData.get(DATA_TRADE_LEFT);
    	if (b > 0) this.spawnAtLocation(new ItemStack(CaerulaArborModItems.TRADER_BOX.get(), b));
    }

	public float canReborn(){
		if (myLlama.isEmpty()) return -1;
		LivingEntity ll = myLlama.get(0);
		Vec3 snapshot = this.position();
		this.teleportTo(ll.getX(), ll.getY(), ll.getZ());
		Level world = this.level();
		world.playSound(null, this.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.HOSTILE,
			1, 0.8f + 0.2f * this.getRandom().nextFloat());
		if (world instanceof ServerLevel sl)
			sl.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, this.getX(), this.getY()+1, this.getZ(), 32, 0.8, 0.8, 0.8, 0.1);
		ll.teleportTo(snapshot.x, snapshot.y, snapshot.z);
		float snapH = ll.getHealth();
		ll.kill();
		return snapH;
	}

	public void setHealth(float pHealth){
		if (pHealth <= 0){
			float h = canReborn();
			if (h > 0){
				super.setHealth(h);
				return;
			}
			if (WanderingAddPhaseProcedure.execute(this)){
				super.setHealth(this.getMaxHealth());
				return;
			}
		}
		super.setHealth(pHealth);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		WanderingSummonLlamaProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataCURRENT_ACTION", this.entityData.get(DATA_CURRENT_ACTION));
		compound.putInt("DataACTION_COOLDOWN", this.entityData.get(DATA_ACTION_COOLDOWN));
		compound.putInt("DataACTION_TICK", this.entityData.get(DATA_ACTION_TICK));
		compound.putInt("DataINVISIBLE_TICK", this.entityData.get(DATA_INVISIBLE_TICK));
		compound.putInt("DataTRADE_LEFT", this.entityData.get(DATA_TRADE_LEFT));
		compound.putBoolean("DataSHOW_BOARD", this.entityData.get(DATA_SHOW_BOARD));
		compound.putInt("DataPHASE", this.entityData.get(DATA_PHASE));
		compound.putInt("DataTRADE_TICK", this.entityData.get(DATA_TRADE_TICK));
		compound.putInt("tickDesp", this.tickDespawn);
		LivingEntity llama0 = myLlama.isEmpty() ? null: myLlama.get(0);
		LivingEntity llama1 = myLlama.size() <= 1 ? null: myLlama.get(1);
		if (llama0 != null) compound.putUUID("llama_0", llama0.getUUID());
		if (llama1 != null) compound.putUUID("llama_1", llama1.getUUID());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataCURRENT_ACTION"))
			this.entityData.set(DATA_CURRENT_ACTION, compound.getInt("DataCURRENT_ACTION"));
		if (compound.contains("DataACTION_COOLDOWN"))
			this.entityData.set(DATA_ACTION_COOLDOWN, compound.getInt("DataACTION_COOLDOWN"));
		if (compound.contains("DataACTION_TICK"))
			this.entityData.set(DATA_ACTION_TICK, compound.getInt("DataACTION_TICK"));
		if (compound.contains("DataINVISIBLE_TICK"))
			this.entityData.set(DATA_INVISIBLE_TICK, compound.getInt("DataINVISIBLE_TICK"));
		if (compound.contains("DataTRADE_LEFT"))
			this.entityData.set(DATA_TRADE_LEFT, compound.getInt("DataTRADE_LEFT"));
		if (compound.contains("DataSHOW_BOARD"))
			this.entityData.set(DATA_SHOW_BOARD, compound.getBoolean("DataSHOW_BOARD"));
		if (compound.contains("DataPHASE"))
			this.entityData.set(DATA_PHASE, compound.getInt("DataPHASE"));
		if (compound.contains("DataTRADE_TICK"))
			this.entityData.set(DATA_TRADE_TICK, compound.getInt("DataTRADE_TICK"));
		if (compound.contains("tickDesp"))
			this.tickDespawn = compound.getInt("tickDesp");
		if (compound.contains("llama_0")) uuid0 = compound.getUUID("llama_0");
		if (compound.contains("llama_1")) uuid1 = compound.getUUID("llama_1");
	}

	public UUID uuid0 = null;
	public UUID uuid1 = null;
	private boolean llamaUnchecked = true;

	@Override
	public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
		ItemStack itemstack = sourceentity.getItemInHand(hand);
		InteractionResult retval = InteractionResult.sidedSuccess(this.level().isClientSide());
		super.mobInteract(sourceentity, hand);
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();
		Entity entity = this;
		Level world = this.level();
		return WanderingTradingProcedure.execute(world, x, y, z, entity, sourceentity);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		WanderingTicksProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public void performRangedAttack(LivingEntity target, float flval) {
		CaerulaArborMod.queueServerWork(9, ()->{
			ComplexChitinTridentThrownEntity.shoot(this, target);
			}
		);
	}

	public int lastAttackTick = 0;
	@Override
	public boolean doHurtTarget(Entity pEntity){
		if (this.tickCount > lastAttackTick && this.tickCount < lastAttackTick + 16) return false;
		lastAttackTick = this.tickCount;
		CaerulaArborMod.queueServerWork(7, ()->{
			if (this.isAlive() && this.distanceToSqr(pEntity) <= 9){
				super.doHurtTarget(pEntity);
				CauseSanityProcedure.execute(this.level(), pEntity.getX(), pEntity.getY(), pEntity.getZ(), pEntity, this);
			}
			}
		);
		return true;
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 63);
		builder = builder.add(Attributes.ARMOR, 6);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
		builder = builder.add(Attributes.FOLLOW_RANGE, 36);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.75);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
			return event.setAndContinue(RawAnimation.begin().thenPlay("die"));
		}
		if (this.animationprocedure.equals("empty")) {
			int p = phase();
			if (p <= 0){
				int act = actionAnim();
				if (act >= 0){
					if (act == 0) return event.setAndContinue(RawAnimation.begin().thenPlay("eat_apple"));
					if (act == 1) return event.setAndContinue(RawAnimation.begin().thenPlay("drink"));
					if (act == 2) return event.setAndContinue(RawAnimation.begin().thenPlay("trident_thunder"));
					if (act == 3) return event.setAndContinue(RawAnimation.begin().thenPlay("summon_llama"));
					if (act == 99) return event.setAndContinue(RawAnimation.begin().thenPlay("give_box"));
				}
				if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.1F && event.getLimbSwingAmount() < 0.1F))) {
					return event.setAndContinue(RawAnimation.begin().thenLoop("move"));
				}
				return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
			} else {
				if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.1F && event.getLimbSwingAmount() < 0.1F))
					&& !this.isAggressive()) {
					return event.setAndContinue(RawAnimation.begin().thenLoop("move2"));
				}
				if (this.isAggressive() && event.isMoving()) {
					return event.setAndContinue(RawAnimation.begin().thenLoop("sprint"));
				}
				return event.setAndContinue(RawAnimation.begin().thenLoop("idle2"));
			}
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
		if (this.swinging && this.lastSwing + 14L <= level().getGameTime()) {
			this.swinging = false;
		}
		if ((this.swinging || this.entityData.get(SHOOT)) && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			if (phase() <= 0) return event.setAndContinue(RawAnimation.begin().thenPlay("trident_throw"));
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

	public boolean isGunmu(){
		return this.entityData.get(DATA_INVISIBLE_TICK) > 0;
	}
	public int phase(){
		return entityData.get(DATA_PHASE);
	}
	public boolean isSignHidden(){
		if (phase() > 0) return true;
		return !entityData.get(DATA_SHOW_BOARD);
	}
	private int actionAnim(){
		if (entityData.get(DATA_ACTION_TICK) <= 0) return -1;
		if (entityData.get(DATA_TRADE_TICK) > 0) return 99;
		return entityData.get(DATA_CURRENT_ACTION);
	}
}
