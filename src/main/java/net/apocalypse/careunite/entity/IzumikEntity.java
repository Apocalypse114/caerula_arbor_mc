
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
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
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
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
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

import net.apocalypse.careunite.procedures.PhasAfter0Procedure;
import net.apocalypse.careunite.procedures.MobAliveProcedure;
import net.apocalypse.careunite.procedures.IzumikTicksProcedure;
import net.apocalypse.careunite.procedures.IzumikDropRelicProcedure;
import net.apocalypse.careunite.procedures.InitIzumikProcedure;
import net.apocalypse.careunite.procedures.GiveAdvIzumikProcedure;
import net.apocalypse.careunite.procedures.FastLearnProcedure;
import net.apocalypse.careunite.procedures.IzumikDoHurtTargetProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import net.apocalypse.careunite.entity.IzumilDollEntity;

import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.Difficulty;

import javax.annotation.Nullable;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.server.level.ServerLevel;
import net.apocalypse.careunite.BossWithBgm;

public class IzumikEntity extends Monster implements GeoEntity, BossWithBgm {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(IzumikEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(IzumikEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(IzumikEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_growth_p = SynchedEntityData.defineId(IzumikEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_skillp = SynchedEntityData.defineId(IzumikEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_phase = SynchedEntityData.defineId(IzumikEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_wave = SynchedEntityData.defineId(IzumikEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_skillp_1 = SynchedEntityData.defineId(IzumikEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_deal = SynchedEntityData.defineId(IzumikEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> T_SINCE_SKILL = SynchedEntityData.defineId(IzumikEntity.class, EntityDataSerializers.INT);

	public int attackCooldown = 0;
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.PINK, ServerBossEvent.BossBarOverlay.NOTCHED_12);
	
	public IzumikEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.IZUMIK.get(), world);
	}

	public static final ResourceLocation bgm = new ResourceLocation("caerula_arbor_reunite:endospore");

	public ResourceLocation getBgm(){
		return bgm;
	}

	public IzumikEntity(EntityType<IzumikEntity> type, Level world) {
		super(type, world);
		xpReward = 128;
		setNoAi(false);
		setMaxUpStep(2f);
		setPersistenceRequired();
		this.moveControl = new FlyingMoveControl(this, 10, true);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "izumik_base");
		this.entityData.define(DATA_growth_p, 0);
		this.entityData.define(DATA_skillp, 5);
		this.entityData.define(DATA_phase, 0);
		this.entityData.define(DATA_wave, 8);
		this.entityData.define(DATA_skillp_1, 100);
		this.entityData.define(DATA_deal, 0);
		this.entityData.define(T_SINCE_SKILL, -1);
	}

	public int tickSinceSkill(){
		int t = entityData.get(T_SINCE_SKILL);
		if (t<0) return -99;
		return this.tickCount - t;
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public String getTexture() {
		return this.entityData.get(TEXTURE);
	}

	@Override
	public boolean canCollideWith(Entity entity) {
		return true;
	}

	@Override
	public boolean canBeCollidedWith() {
		Entity entity = this;
		Level world = entity.level();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		return MobAliveProcedure.execute(entity);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		return new FlyingPathNavigation(this, world);
	}

	

	public static boolean getCanAttack(Entity entity){
		return PhasAfter0Procedure.execute(entity) && entity instanceof IzumikEntity izumik && izumik.attackCooldown <= 0;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this) {
			@Override
			public boolean canUse() {
				Entity entity = IzumikEntity.this;
				Level world = IzumikEntity.this.level();
				return super.canUse() && getCanAttack(entity);
			}

			@Override
			public boolean canContinueToUse() {
				Entity entity = IzumikEntity.this;
				Level world = IzumikEntity.this.level();
				return super.canContinueToUse() && getCanAttack(entity);
			}
		});
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.15, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 156.25;
			}

			@Override
			public boolean canUse() {
				Entity entity = IzumikEntity.this;
				Level world = IzumikEntity.this.level();
				return super.canUse() && getCanAttack(entity);
			}

			@Override
			public boolean canContinueToUse() {
				Entity entity = IzumikEntity.this;
				Level world = IzumikEntity.this.level();
				return super.canContinueToUse() && getCanAttack(entity);
			}

		});
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
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

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:izumik_ambient"));
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:izumik_hit"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:izumik_die"));
	}

	@Override
	public boolean causeFallDamage(float l, float d, DamageSource source) {
		return false;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		GiveAdvIzumikProcedure.execute(this.level(), this);
		if (source.is(DamageTypes.IN_FIRE))
			return false;
		if (source.getDirectEntity() instanceof ThrownPotion || source.getDirectEntity() instanceof AreaEffectCloud)
			return false;
		if (source.is(DamageTypes.CACTUS))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		if (source.is(DamageTypes.LIGHTNING_BOLT))
			return false;
		if (source.is(DamageTypes.EXPLOSION))
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
		this.attackCooldown = 50;
		IzumikDoHurtTargetProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), pEntity, this);
		return true;
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		GiveAdvIzumikProcedure.execute(this.level(), this);
	}

	@Override
    protected void dropCustomDeathLoot(DamageSource p_21018_, int p_21019_, boolean p_21020_) {
    	if(this.level() instanceof ServerLevel _level){
    		Entity entityToSpawn = CaerulaArborModEntities.IZUMIL_DOLL.get()
    			.spawn(_level, this.blockPosition(), MobSpawnType.MOB_SUMMONED);
    		if (entityToSpawn instanceof IzumilDollEntity _datEntSetL)
				_datEntSetL.getEntityData().set(IzumilDollEntity.DATA_CanLoot, true);

    	}
    }

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		InitIzumikProcedure.execute(this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("Datagrowth_p", this.entityData.get(DATA_growth_p));
		compound.putInt("Dataskillp", this.entityData.get(DATA_skillp));
		compound.putInt("Dataphase", this.entityData.get(DATA_phase));
		compound.putInt("Datawave", this.entityData.get(DATA_wave));
		compound.putInt("Dataskillp_1", this.entityData.get(DATA_skillp_1));
		compound.putInt("Datadeal", this.entityData.get(DATA_deal));
		compound.putInt("DataCooldown", this.attackCooldown);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("Datagrowth_p"))
			this.entityData.set(DATA_growth_p, compound.getInt("Datagrowth_p"));
		if (compound.contains("Dataskillp"))
			this.entityData.set(DATA_skillp, compound.getInt("Dataskillp"));
		if (compound.contains("Dataphase"))
			this.entityData.set(DATA_phase, compound.getInt("Dataphase"));
		if (compound.contains("Datawave"))
			this.entityData.set(DATA_wave, compound.getInt("Datawave"));
		if (compound.contains("Dataskillp_1"))
			this.entityData.set(DATA_skillp_1, compound.getInt("Dataskillp_1"));
		if (compound.contains("Datadeal"))
			this.entityData.set(DATA_deal, compound.getInt("Datadeal"));
		if (compound.contains("DataCooldown"))
			this.attackCooldown = compound.getInt("DataCooldown");

	}

	@Override
	public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
		InteractionResult res = super.mobInteract(sourceentity, hand);
		Level world = this.level();
		if (!world.isClientSide()) return FastLearnProcedure.execute(this, sourceentity);
		return res;
	}

	@Override
	public void baseTick() {
		super.baseTick();
		this.attackCooldown --;
		boolean shocking = IzumikTicksProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		if (!this.level().isClientSide() && shocking && this.entityData.get(T_SINCE_SKILL) < this.tickCount){
			this.entityData.set(T_SINCE_SKILL, this.tickCount);
		}
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

	public void push(double pX,double pY,double pZ){
		super.push(pX * 0.25, pY * 0.125, pZ * 0.25);
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
		super.setNoGravity(true);
	}

	public void aiStep() {
		super.aiStep();
		this.setNoGravity(true);
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.6);
		builder = builder.add(Attributes.MAX_HEALTH, 600);
		builder = builder.add(Attributes.ARMOR, 18);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 22);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 10);
		builder = builder.add(Attributes.FLYING_SPEED, 0.6);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.izumik.die1"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.izumik.idle"));
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
		if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.izumik.attack_1"));
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
		if (this.deathTime >= 30) {
			this.remove(IzumikEntity.RemovalReason.KILLED);
			this.dropExperience();
			IzumikDropRelicProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ());
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

	public ResourceKey<DamageType> oceankiller = ResourceKey.create(Registries.DAMAGE_TYPE, 
							new ResourceLocation("caerula_arbor_reunite:oceankiller_damage"));

	@Override
	public void remove(RemovalReason pReason){
		if(this.level().getDifficulty() != Difficulty.PEACEFUL && pReason == RemovalReason.DISCARDED){
			this.hurt(
				new DamageSource(
					this.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(oceankiller)
				),
				20
			);
			return;
		} else if (pReason == RemovalReason.KILLED && !this.isDeadOrDying()) return;	
		super.remove(pReason);
	}

	@Override
    public void setHealth(float pHealth){
    	float hlth = this.getHealth();
    	float mhlth = this.getMaxHealth();
        if(this.hasEffect(CaerulaArborModMobEffects.INVULNERABLE.get()) && pHealth < this.getHealth()) return;
        float reduction = hlth - pHealth;
        float finalV = reduction >= mhlth * 0.33f ? hlth - mhlth * 0.33f : hlth - reduction;
        super.setHealth(finalV);
    }

    public int getPhase(){
    	return this.entityData.get(DATA_phase);
    }
}
