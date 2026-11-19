
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
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.procedures.DragonBreathPurchaseProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.world.entity.Entity;

public class MoistDragonBreathEntity extends PathfinderMob implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(MoistDragonBreathEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(MoistDragonBreathEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(MoistDragonBreathEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> DATA_TARGET = SynchedEntityData.defineId(MoistDragonBreathEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> DATA_OWNER = SynchedEntityData.defineId(MoistDragonBreathEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_TYPE = SynchedEntityData.defineId(MoistDragonBreathEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public MoistDragonBreathEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.MOIST_DRAGON_BREATH.get(), world);
	}

	public MoistDragonBreathEntity(EntityType<MoistDragonBreathEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
		setMaxUpStep(0.6f);
		this.moveControl = new FlyingMoveControl(this, 10, true);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "moist_dragon_ball");
		this.entityData.define(DATA_TARGET, "");
		this.entityData.define(DATA_OWNER, "");
		this.entityData.define(DATA_TYPE, 0);
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
	protected PathNavigation createNavigation(Level world) {
		return new FlyingPathNavigation(this, world);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();

	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
	}

	@Override
	public boolean causeFallDamage(float l, float d, DamageSource source) {
		return false;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putString("DataTARGET", this.entityData.get(DATA_TARGET));
		compound.putString("DataOWNER", this.entityData.get(DATA_OWNER));
		compound.putInt("DataTYPE", this.entityData.get(DATA_TYPE));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataTARGET"))
			this.entityData.set(DATA_TARGET, compound.getString("DataTARGET"));
		if (compound.contains("DataOWNER"))
			this.entityData.set(DATA_OWNER, compound.getString("DataOWNER"));
		if (compound.contains("DataTYPE"))
			this.entityData.set(DATA_TYPE, compound.getInt("DataTYPE"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		DragonBreathPurchaseProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public void tick() {
      this.noPhysics = true;
      super.tick();
      this.noPhysics = false;
      this.setNoGravity(true);
	}
	
	@Override
	public void move(MoverType pType, Vec3 pPos) {
      super.move(pType, pPos);
      this.checkInsideBlocks();
   }

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	@Override
	public void setNoGravity(boolean ignored) {
		super.setNoGravity(true);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.updateSwingTime();
		this.setNoGravity(true);
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 24);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 14);
		builder = builder.add(Attributes.FOLLOW_RANGE, 36);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 10);
		builder = builder.add(Attributes.FLYING_SPEED, 0.3);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.moist_dragon_breath.idle"));
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
		if (this.deathTime == 5) {
			this.remove(MoistDragonBreathEntity.RemovalReason.KILLED);
			this.dropExperience();
		}
	}

	@Override
	public boolean hurt(DamageSource source, float amount){
		if (source.getEntity() == null) return false;
		return super.hurt(source, amount);
	}

	@Override
    public void setHealth(float pHealth){
    	if (this.entityData.get(DATA_TYPE) ==1 ){
	    	float hlth = this.getHealth();
	        float reduction = hlth - pHealth;
	        super.setHealth(reduction >= 1 ? hlth - 1 : pHealth);
	        return;
    	}
    	super.setHealth(pHealth);
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

	public String getSyncedAnimation() {
		return this.entityData.get(ANIMATION);
	}

	public void setAnimation(String animation) {
		this.entityData.set(ANIMATION, animation);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar data) {
		data.add(new AnimationController<>(this, "movement", 4, this::movementPredicate));
		data.add(new AnimationController<>(this, "procedure", 4, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
