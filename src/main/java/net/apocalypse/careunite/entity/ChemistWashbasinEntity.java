
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

import net.apocalypse.careunite.procedures.BasinTickProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.AlchemyCreation;
import net.apocalypse.careunite.init.CaerulaArborModSounds;
import net.apocalypse.careunite.CaerulaArborMod;
import net.minecraft.util.Mth;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;

public class ChemistWashbasinEntity extends AlchemyCreation implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(ChemistWashbasinEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(ChemistWashbasinEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(ChemistWashbasinEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_BASE_HELD = SynchedEntityData.defineId(ChemistWashbasinEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_ATTACK_ANIM = SynchedEntityData.defineId(ChemistWashbasinEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_DURA = SynchedEntityData.defineId(ChemistWashbasinEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	private static final ResourceLocation TP = new ResourceLocation("caerula_arbor_reunite", "textures/entities/chemist_washbasin.png");
	private static final ResourceLocation T0 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/chemist_washbasin_0.png");
	private static final ResourceLocation T1 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/chemist_washbasin_1.png");
	private static final ResourceLocation T2 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/chemist_washbasin_2.png");
	private static final ResourceLocation T3 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/chemist_washbasin_3.png");

	private static final ResourceLocation LP = new ResourceLocation("caerula_arbor_reunite", "textures/entities/basin_lit.png");
	private static final ResourceLocation L0 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/basin_lit_0.png");
	private static final ResourceLocation L1 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/basin_lit_1.png");
	private static final ResourceLocation L2 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/basin_lit_2.png");
	private static final ResourceLocation L3 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/basin_lit_3.png");

	public ChemistWashbasinEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.CHEMIST_WASHBASIN.get(), world);
	}

	public ChemistWashbasinEntity(EntityType<ChemistWashbasinEntity> type, Level world) {
		super(type, world);
		xpReward = 9;
		setNoAi(false);
		setMaxUpStep(0.7f);
		setPersistenceRequired();
	}

	public int extractionCooldown(){
    	return 120;
    }

    public int injectionType(){
    	return 1;
    }

	public void setStunned(boolean st){
		if (st) this.setAnimation("drop");
		else this.setAnimation("revive");
		super.setStunned(st);
	}

	public int maxBase(){
		return 8;
	}

	public boolean canOverflow(){
		return true;
	}

	public void onOverflow(){
		lostBase(8, true);
		super.onOverflow();
	}

	public ResourceLocation getTextureRes() {
		if (isStunned()) return TP;
		int b = this.entityData.get(DATA_BASE_HELD);
		if (b <= 1) return T0;
		if (b <= 4) return T1;
		if (b <= 6) return T2;
		return T3;
	}

	public ResourceLocation getGlowmask() {
		if (isStunned()) return LP;
		int b = this.entityData.get(DATA_BASE_HELD);
		if (b <= 1) return L0;
		if (b <= 4) return L1;
		if (b <= 6) return L2;
		return L3;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "chemist_washbasin");
		this.entityData.define(DATA_BASE_HELD, 0);
		this.entityData.define(DATA_ATTACK_ANIM, 0);
		this.entityData.define(DATA_DURA, 0);
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
		this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1, false) {
			@Override
			public boolean canUse(){
				return super.canUse() && ChemistWashbasinEntity.this.canWeAttack();
			}
			@Override
			public boolean canContinueToUse(){
				return super.canContinueToUse() && ChemistWashbasinEntity.this.canWeAttack();
			}
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 2.89;
			}
		});
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(5, new FloatGoal(this));
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
		return super.hurt(source, amount);
	}

	private int hitCount = 0;
	public void actuallyHurt(DamageSource source, float amount){
		int b;
		if (!source.is(DamageTypeTags.BYPASSES_EFFECTS) 
			&& !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)
			&& !source.is(DamageTypeTags.BYPASSES_RESISTANCE)){
			b = Mth.clamp(getBaseHeld(), 0, 8);
			amount *= 1 - b * 0.1f;
		}
		super.actuallyHurt(source, amount);
		hitCount ++;
		if (!this.isStunned() && !this.isDeadOrDying() && hitCount >= 5){
			this.gainBase(1);
			hitCount = 0;
		}
	}

	public boolean canWeAttack(){
		return !isStunned() && entityData.get(DATA_DURA) <= 0;
	}

	@Override
	public boolean doHurtTarget(Entity pEntity){
		this.entityData.set(DATA_DURA, 30);
		this.entityData.set(DATA_ATTACK_ANIM, 15);
		CaerulaArborMod.queueServerWork(9, ()->{
			if (pEntity != null && !this.isStunned() && !this.isDeadOrDying() && this.distanceToSqr(pEntity) <= 4)
				super.doHurtTarget(pEntity);
			}
		);
		return true;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataBASE_HELD", this.entityData.get(DATA_BASE_HELD));
		compound.putInt("DataATTACK_ANIM", this.entityData.get(DATA_ATTACK_ANIM));
		compound.putInt("DataDURA", this.entityData.get(DATA_DURA));
		compound.putInt("hitCount", hitCount);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataBASE_HELD"))
			this.entityData.set(DATA_BASE_HELD, compound.getInt("DataBASE_HELD"));
		if (compound.contains("DataATTACK_ANIM"))
			this.entityData.set(DATA_ATTACK_ANIM, compound.getInt("DataATTACK_ANIM"));
		if (compound.contains("DataDURA"))
			this.entityData.set(DATA_DURA, compound.getInt("DataDURA"));
		if (compound.contains("hitCount"))
			hitCount = compound.getInt("hitCount");
	}

	@Override
	public void baseTick() {
		super.baseTick();
		BasinTickProcedure.execute(this.level(), this);
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 192);
		builder = builder.add(Attributes.ARMOR, 3);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 17);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
			return event.setAndContinue(RawAnimation.begin().thenPlay("die"));
		}
		if (this.animationprocedure.equals("empty")) {
			if (this.isStunned()) return event.setAndContinue(RawAnimation.begin().thenLoop("as_item"));
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
		if (this.swinging && this.lastSwing + 15L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (entityData.get(DATA_ATTACK_ANIM) > 0 && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
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

	@Override
	protected void tickDeath() {
		super.tickDeath();
		if (deathTime == 15){
			lostBase(8, true);
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
		data.add(new AnimationController<>(this, "attacking", 1, this::attackingPredicate));
		data.add(new AnimationController<>(this, "procedure", 1, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
