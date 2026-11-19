
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
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.monster.Endermite;
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
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.InitSmagicProcedure;
import net.apocalypse.careunite.procedures.EndermanRetreatProcedure;
import net.apocalypse.careunite.procedures.EndermanLaserSkillProcedure;
import net.apocalypse.careunite.procedures.EndermanDropCreeperProcedure;
import net.apocalypse.careunite.procedures.DropASliderProcedure;
import net.apocalypse.careunite.procedures.EndermanTeleportProcedure;
import net.apocalypse.careunite.procedures.EndermanRandomtTeleProcedure;
import net.apocalypse.careunite.procedures.EndermanDoHurtTargetProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import javax.annotation.Nullable;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.apocalypse.careunite.Seaborn;

public class OceanizedEndermanEntity extends Monster implements GeoEntity, Seaborn {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(OceanizedEndermanEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(OceanizedEndermanEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(OceanizedEndermanEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_skillp = SynchedEntityData.defineId(OceanizedEndermanEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_cooldown = SynchedEntityData.defineId(OceanizedEndermanEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_HOLDING_CrEEPER = SynchedEntityData.defineId(OceanizedEndermanEntity.class, EntityDataSerializers.BOOLEAN);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	private boolean creeperCharged;
	public String animationprocedure = "empty";

	public OceanizedEndermanEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.OCEANIZED_ENDERMAN.get(), world);
	}

	public Seaborn.Type getSeabornType() {
    	return Seaborn.Type.CONVERTED;
    }

    public Seaborn.Tier getTier(){
        return Seaborn.Tier.ELITE;
    }

	public OceanizedEndermanEntity(EntityType<OceanizedEndermanEntity> type, Level world) {
		super(type, world);
		xpReward = 8;
		setNoAi(false);
		setMaxUpStep(1f);
		setPersistenceRequired();
		creeperCharged = false;
	}

	public boolean getCreeperCharged(){
		return creeperCharged;
	}

	public void setCreeperCharged(){
		creeperCharged = true;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "oceanzied_enderman");
		this.entityData.define(DATA_skillp, 150);
		this.entityData.define(DATA_cooldown, 200);
		this.entityData.define(DATA_HOLDING_CrEEPER, false);
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
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.33, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 6.25;
			}
		});
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Endermite.class, true, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, IronGolem.class, true, false));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, SnowGolem.class, true, false));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, Villager.class, true, false));
		this.targetSelector.addGoal(7, new NearestAttackableTargetGoal(this, Illusioner.class, true, false));
		this.targetSelector.addGoal(8, new NearestAttackableTargetGoal(this, Pillager.class, true, false));
		this.targetSelector.addGoal(9, new NearestAttackableTargetGoal(this, Vindicator.class, true, false));
		this.targetSelector.addGoal(10, new NearestAttackableTargetGoal(this, Witch.class, true, false));
		this.targetSelector.addGoal(11, new NearestAttackableTargetGoal(this, Piglin.class, true, false));
		this.targetSelector.addGoal(12, new NearestAttackableTargetGoal(this, PiglinBrute.class, true, false));
		this.targetSelector.addGoal(13, new NearestAttackableTargetGoal(this, ZombifiedPiglin.class, true, false));
		this.goalSelector.addGoal(14, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(15, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(16, new FloatGoal(this));
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
		return SoundEvents.ENDERMAN_AMBIENT;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return SoundEvents.ENDERMAN_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.ENDERMAN_DEATH;
	}

	private int tpCooldown = 0;
	private int immuneToArrowTpCooldown = 0;
	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (tpCooldown <= 0){
			EndermanTeleportProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this, source.getEntity());
			tpCooldown = 5;
		}
		if (source.is(DamageTypes.FALL))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		boolean flag;
		if (immuneToArrow(source)){
			flag = false;
		} else if (this.isHolding() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)){
			flag = super.hurt(source, amount * 0.8f);
		} else {
			flag = super.hurt(source, amount);
		}
		if (flag) EndermanRetreatProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		return flag;
	}

	@Override
	public boolean doHurtTarget(Entity pEntity){
		EndermanDoHurtTargetProcedure.execute(this.level(), pEntity, this);
		return true;
	}

	TagKey<DamageType> ANTI_ENDER = TagKey.create(Registries.DAMAGE_TYPE, 
			new ResourceLocation("caerula_arbor_reunite:bypasses_enderman"));

	public boolean immuneToArrow(DamageSource damageSource){
		if (damageSource.is(ANTI_ENDER))
			return false;
		if (this.hasEffect(CaerulaArborModMobEffects.DIZZY.get()) || this.hasEffect(CaerulaArborModMobEffects.MUTE.get()))
			return false;
		Entity immediatesourceentity = damageSource.getDirectEntity();
		Entity sourceentity = damageSource.getEntity();
		if (sourceentity == null) return false;
		if (immediatesourceentity != sourceentity || !(sourceentity instanceof LivingEntity)) {
			if (sourceentity instanceof LivingEntity _ent)
				this.setTarget(_ent);
			if (immuneToArrowTpCooldown <= 0){
				EndermanRandomtTeleProcedure.execute(this.level(),this.getX(),this.getY(),this.getZ(),this);
				immuneToArrowTpCooldown = 5;
			}
			return true;
		}
		return false;
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		EndermanDropCreeperProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		if (!isHolding() && this.getRandom().nextDouble() < 0.05) this.entityData.set(DATA_HOLDING_CrEEPER, true);
		InitSmagicProcedure.execute(this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("Dataskillp", this.entityData.get(DATA_skillp));
		compound.putInt("Datacooldown", this.entityData.get(DATA_cooldown));
		compound.putBoolean("DataHOLDING_CrEEPER", this.entityData.get(DATA_HOLDING_CrEEPER));
		compound.putBoolean("CreeperCharged", getCreeperCharged());
	}

	public void tick(){
		super.tick();
		tpCooldown--;
		immuneToArrowTpCooldown--;
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("Dataskillp"))
			this.entityData.set(DATA_skillp, compound.getInt("Dataskillp"));
		if (compound.contains("Datacooldown"))
			this.entityData.set(DATA_cooldown, compound.getInt("Datacooldown"));
		if (compound.contains("DataHOLDING_CrEEPER"))
			this.entityData.set(DATA_HOLDING_CrEEPER, compound.getBoolean("DataHOLDING_CrEEPER"));
		if (compound.contains("CreeperCharged") && compound.getBoolean("CreeperCharged"))
			this.setCreeperCharged();
	}

	@Override
	public void baseTick() {
		super.baseTick();
		EndermanLaserSkillProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 95);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 14);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.45);
		return builder;
	}

	public boolean isHolding(){
		return this.entityData.get(DATA_HOLDING_CrEEPER);
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.oceanzied_enderman.die"));
		}
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.1F && event.getLimbSwingAmount() < 0.1F))
					&& !this.isAggressive()) {
				if (this.isHolding()) return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanzied_enderman.walk_hold"));
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanzied_enderman.walk"));
			}
			if (this.isAggressive() && event.isMoving()) {
				if (this.isHolding()) return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanzied_enderman.sprint_hold"));
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanzied_enderman.sprint"));
			}
			if (this.isHolding()) return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanzied_enderman.idle_hold"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanzied_enderman.idle"));
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
			if (this.isHolding()) return event.setAndContinue(RawAnimation.begin().thenPlay("animation.oceanzied_enderman.attack_hold"));
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.oceanzied_enderman.attack"));
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
		if (this.deathTime == 20) {
			DropASliderProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
			this.remove(OceanizedEndermanEntity.RemovalReason.KILLED);
			this.dropExperience();
		}
	}

	public String getSyncedAnimation() {
		return this.entityData.get(ANIMATION);
	}

	public void setAnimation(String animation) {
		if (animation.equals("animation.oceanzied_enderman.skill") && this.isHolding()) animation += "_hold";
		this.entityData.set(ANIMATION, animation);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar data) {
		data.add(new AnimationController<>(this, "movement", 1, this::movementPredicate));
		data.add(new AnimationController<>(this, "attacking", 0, this::attackingPredicate));
		data.add(new AnimationController<>(this, "procedure", 1, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
