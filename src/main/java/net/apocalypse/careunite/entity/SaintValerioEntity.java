
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
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
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.ValerioTicksProcedure;
import net.apocalypse.careunite.procedures.ValerioNextPhaseProcedure;
import net.apocalypse.careunite.procedures.DoElementalBurnProcedure;
import net.apocalypse.careunite.procedures.DealZhixuBurnProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.AlchemyCreation;
import net.apocalypse.careunite.init.CaerulaArborModSounds;
import net.apocalypse.careunite.CaerulaArborMod;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;

public class SaintValerioEntity extends AlchemyCreation implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(SaintValerioEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(SaintValerioEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(SaintValerioEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_PHASE = SynchedEntityData.defineId(SaintValerioEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_IGNITE_P = SynchedEntityData.defineId(SaintValerioEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_SKILL_PIERCE_T = SynchedEntityData.defineId(SaintValerioEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_SKILL_PIERCE_COOLDOWN = SynchedEntityData.defineId(SaintValerioEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_SKILL_SHOOT_T = SynchedEntityData.defineId(SaintValerioEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_SKILL_SHOOT_COOLDOWN = SynchedEntityData.defineId(SaintValerioEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_ATTACK_ANIM = SynchedEntityData.defineId(SaintValerioEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_IGNITE_T = SynchedEntityData.defineId(SaintValerioEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_REVIVE_T = SynchedEntityData.defineId(SaintValerioEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public float[] yaws;
	public float[] pitches;
	public static float[] genNums(RandomSource so){
		float[] res = new float[9];
		for (int i = 0; i<9;i++){
			res[i] = so.nextFloat() * 360;
		}
		return res;
	}

	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.BLUE, ServerBossEvent.BossBarOverlay.PROGRESS);

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

	public SaintValerioEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.SAINT_VALERIO.get(), world);
	}

	public SaintValerioEntity(EntityType<SaintValerioEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
		setMaxUpStep(1f);
		setPersistenceRequired();
		setBaseHeld(3);
		yaws = genNums(this.getRandom());
		pitches = genNums(this.getRandom());
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "saint_warrerio_0");
		this.entityData.define(DATA_PHASE, 0);
		this.entityData.define(DATA_IGNITE_P, 100);
		this.entityData.define(DATA_SKILL_PIERCE_T, 5);
		this.entityData.define(DATA_SKILL_PIERCE_COOLDOWN, 0);
		this.entityData.define(DATA_SKILL_SHOOT_T, 0);
		this.entityData.define(DATA_SKILL_SHOOT_COOLDOWN, 300);
		this.entityData.define(DATA_ATTACK_ANIM, 0);
		this.entityData.define(DATA_IGNITE_T, 0);
		this.entityData.define(DATA_REVIVE_T, 0);
	}

	public boolean canInject(){
		if (inAnySkill()) return false;
    	return super.canInject();
    }

    public boolean shouldTryAbsorb(){
    	if (inAnySkill()) return false;
    	return super.shouldTryAbsorb();
    }

	public boolean canOverflow(){
		return true;
	}

	public int maxBase(){
		return getPhase() > 0 ? 7: 6;
	}

	public int extractionCooldown(){
    	return 200;
    }

    public int injectionType(){
    	return 2;
    }

	public boolean inPierceSKill(){
		return entityData.get(DATA_SKILL_PIERCE_T) > 0;
	}
	public int pierceSkillTime(){
		return 75 - entityData.get(DATA_SKILL_PIERCE_T);
	}
	public boolean inShootSKill(){
		return entityData.get(DATA_SKILL_SHOOT_T) > 0;
	}
	public int shootSkillTime(){
		return 82 - entityData.get(DATA_SKILL_SHOOT_T);
	}
	public boolean inIgnition(){
		return entityData.get(DATA_IGNITE_T) > 0;
	}
	public int reviveTick(){
		return entityData.get(DATA_REVIVE_T);
	}
	public boolean inAnySkill(){
		return inPierceSKill() || inShootSKill() || reviveTick() > 0;
	}

	public boolean mayAttack(){
		return entityData.get(DATA_ATTACK_ANIM) <= 0 && entityData.get(DATA_IGNITE_T) <= 0 && !inAnySkill();
	}

	public void lostBase(int count, boolean release){
    	if (inAnySkill()) return;
    	super.lostBase(count, release);
    }

    public void gainBase(int count){
    	if (inAnySkill()) return;
    	super.gainBase(count);
    }

    public void setStunned(boolean st){
    	boolean prevSt = isStunned();
		if (!prevSt && st) this.setAnimation("empty");
		super.setStunned(st);
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

	public boolean canBeAffected(MobEffectInstance inst){
		if (inst.getEffect() == CaerulaArborModMobEffects.DIZZY.get()) return false;
		return super.canBeAffected(inst);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Monster.class, false, false));
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 7.29;
			}
			@Override
			public boolean canUse(){
				return super.canUse() && SaintValerioEntity.this.mayAttack();
			}
			@Override
			public boolean canContinueToUse(){
				return super.canContinueToUse() && SaintValerioEntity.this.mayAttack();
			}
		});
		this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1){
			@Override
			public boolean canUse(){
				return super.canUse() && SaintValerioEntity.this.mayAttack();
			}
			@Override
			public boolean canContinueToUse(){
				return super.canContinueToUse() && SaintValerioEntity.this.mayAttack();
			}
		});
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this){
			@Override
			public boolean canUse(){
				return super.canUse() && SaintValerioEntity.this.mayAttack();
			}
			@Override
			public boolean canContinueToUse(){
				return super.canContinueToUse() && SaintValerioEntity.this.mayAttack();
			}
		});
		this.goalSelector.addGoal(6, new FloatGoal(this));
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
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.DROWN))
			return false;
		if (source.is(DamageTypes.FALL))
			return false;
		if (!source.is(DamageTypeTags.BYPASSES_EFFECTS) 
			&& !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)
			&& !source.is(DamageTypeTags.BYPASSES_RESISTANCE)){
			float mult = 1 - getBaseHeld() * 0.12f;
			mult = Mth.clamp(mult, 0.1f, 1);
			amount *= mult;
		}
		return super.hurt(source, amount);
	}
    public ResourceKey key = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_magic"));

	@Override
	public boolean doHurtTarget(Entity pEntity){
		this.entityData.set(DATA_ATTACK_ANIM, 27);
		this.entityData.set(DATA_SKILL_PIERCE_COOLDOWN, Math.max(0, entityData.get(DATA_SKILL_PIERCE_COOLDOWN)-1));
		this.level().playSound(null, this.blockPosition(), CaerulaArborModSounds.VALERIO_PRE.get(), SoundSource.NEUTRAL, 1.25f,
			Mth.nextFloat(this.getRandom(), 0.9f, 1.2f));
		CaerulaArborMod.queueServerWork(12, ()->{
			if (!this.isStunned() && !this.isDeadOrDying())
				pEntity.invulnerableTime = 0;
				if (this.level() instanceof ServerLevel _level)
					_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.PRESAINT_HIT.get()), 
						pEntity.getX(), (pEntity.getY() + pEntity.getBbHeight() * 0.5), pEntity.getZ(), 1, 0.2, 0.2, 0.2, 0);
				if (this.getPhase() > 0){
					AttributeInstance inst = this.getAttribute(Attributes.ATTACK_DAMAGE);
					float amt = inst == null ? 7: (float)inst.getValue();
					if (pEntity.hurt(new DamageSource(
							this.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE)
								.getHolderOrThrow(key), this), amt)){
						if (!DealZhixuBurnProcedure.execute(pEntity, amt * 20)) DoElementalBurnProcedure.execute(this.level(), pEntity, 60);
					}
				} else super.doHurtTarget(pEntity);
				this.level().playSound(null, this.blockPosition(), CaerulaArborModSounds.VALERIO_HIT.get(), SoundSource.NEUTRAL, 1.25f,
					Mth.nextFloat(this.getRandom(), 0.9f, 1.1f));
			}
		);
		return true;
	}

	@Override
	public SoundEvent getDeathSound() {
		return CaerulaArborModSounds.VALERIO_DIE.get();
	}

	public void setHealth(float pHealth){
		if (pHealth <= 0 && ValerioNextPhaseProcedure.execute(this)){
			super.setHealth(1);
			setBaseHeld(4);
			gainBase(0);
			this.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 199, 0, false, false));
			return;
		}
		super.setHealth(pHealth);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataPHASE", this.entityData.get(DATA_PHASE));
		compound.putInt("DataIGNITE_P", this.entityData.get(DATA_IGNITE_P));
		compound.putInt("DataSKILL_PIERCE_T", this.entityData.get(DATA_SKILL_PIERCE_T));
		compound.putInt("DataSKILL_PIERCE_COOLDOWN", this.entityData.get(DATA_SKILL_PIERCE_COOLDOWN));
		compound.putInt("DataSKILL_SHOOT_T", this.entityData.get(DATA_SKILL_SHOOT_T));
		compound.putInt("DataSKILL_SHOOT_COOLDOWN", this.entityData.get(DATA_SKILL_SHOOT_COOLDOWN));
		compound.putInt("DataATTACK_ANIM", this.entityData.get(DATA_ATTACK_ANIM));
		compound.putInt("DataIGNITE_T", this.entityData.get(DATA_IGNITE_T));
		compound.putInt("DataREVIVE_T", this.entityData.get(DATA_REVIVE_T));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataPHASE"))
			this.entityData.set(DATA_PHASE, compound.getInt("DataPHASE"));
		if (compound.contains("DataIGNITE_P"))
			this.entityData.set(DATA_IGNITE_P, compound.getInt("DataIGNITE_P"));
		if (compound.contains("DataSKILL_PIERCE_T"))
			this.entityData.set(DATA_SKILL_PIERCE_T, compound.getInt("DataSKILL_PIERCE_T"));
		if (compound.contains("DataSKILL_PIERCE_COOLDOWN"))
			this.entityData.set(DATA_SKILL_PIERCE_COOLDOWN, compound.getInt("DataSKILL_PIERCE_COOLDOWN"));
		if (compound.contains("DataSKILL_SHOOT_T"))
			this.entityData.set(DATA_SKILL_SHOOT_T, compound.getInt("DataSKILL_SHOOT_T"));
		if (compound.contains("DataSKILL_SHOOT_COOLDOWN"))
			this.entityData.set(DATA_SKILL_SHOOT_COOLDOWN, compound.getInt("DataSKILL_SHOOT_COOLDOWN"));
		if (compound.contains("DataATTACK_ANIM"))
			this.entityData.set(DATA_ATTACK_ANIM, compound.getInt("DataATTACK_ANIM"));
		if (compound.contains("DataIGNITE_T"))
			this.entityData.set(DATA_IGNITE_T, compound.getInt("DataIGNITE_T"));
		if (compound.contains("DataREVIVE_T"))
			this.entityData.set(DATA_REVIVE_T, compound.getInt("DataREVIVE_T"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		if (!isStunned()) ValerioTicksProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		else entityData.set(DATA_ATTACK_ANIM, 0);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale(1, 1.1f);
	}

	public static void init() {
	}

	@Override
	public void customServerAiStep() {
		super.customServerAiStep();
		this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
		if (getPhase() > 0) bossInfo.setColor(ServerBossEvent.BossBarColor.WHITE);
		else bossInfo.setColor(ServerBossEvent.BossBarColor.BLUE);
	}


	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.22);
		builder = builder.add(Attributes.MAX_HEALTH, 360);
		builder = builder.add(Attributes.ARMOR, 13);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 7);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		return builder;
	}

	public int getPhase(){
		return entityData.get(DATA_PHASE);
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.saintvalerio.die"));
		}
		if (this.entityData.get(DATA_REVIVE_T) > 0){
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.saintvalerio.revive"));
		}
		if (this.isStunned()){
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.saintvalerio.overflow"));
		}
		if (this.inShootSKill()){
			if (getPhase() > 0) return event.setAndContinue(RawAnimation.begin().thenPlay("animation.saintvalerio.skill_1_B"));
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.saintvalerio.skill_1_A"));
		}
		if (this.inPierceSKill()){
			if (getPhase() > 0) return event.setAndContinue(RawAnimation.begin().thenPlay("animation.saintvalerio.skill_2_B"));
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.saintvalerio.skill_2_A"));
		}
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.1F && event.getLimbSwingAmount() < 0.1F))
) {
				if (getPhase() > 0) return event.setAndContinue(RawAnimation.begin().thenLoop("animation.saintvalerio.move_B"));
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.saintvalerio.move_A"));
			}
			if (getPhase() > 0) return event.setAndContinue(RawAnimation.begin().thenLoop("animation.saintvalerio.idle_B"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.saintvalerio.idle_A"));
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
		if (this.swinging && this.lastSwing + 27L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.entityData.get(DATA_ATTACK_ANIM) > 0 && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			if (getPhase() > 0) return event.setAndContinue(RawAnimation.begin().thenPlay("animation.saintvalerio.attack_B"));
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.saintvalerio.attack_A"));
		}
		return PlayState.CONTINUE;
	}

	private PlayState hairAnim(AnimationState event){
		return event.setAndContinue(RawAnimation.begin().thenLoop("animation.saintvalerio.hair"));
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
		data.add(new AnimationController<>(this, "hair", 0, this::hairAnim));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
