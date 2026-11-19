
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

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.FloatGoal;

public class SliderBallHugeEntity extends PathfinderMob implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(SliderBallHugeEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(SliderBallHugeEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(SliderBallHugeEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Float> DATA_ROTATION = SynchedEntityData.defineId(SliderBallHugeEntity.class, EntityDataSerializers.FLOAT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public Entity mostRecentAttacker = null;
	public String animationprocedure = "empty";

	public SliderBallHugeEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.SLIDER_BALL_HUGE.get(), world);
	}

	public SliderBallHugeEntity(EntityType<SliderBallHugeEntity> type, Level world) {
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
		this.entityData.define(TEXTURE, "slider_ball_big");
		this.entityData.define(DATA_ROTATION, 0f);
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

		this.goalSelector.addGoal(0, new FloatGoal(this));
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
		return SoundEvents.PLAYER_ATTACK_KNOCKBACK;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putFloat("DataROTATION", this.entityData.get(DATA_ROTATION));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataROTATION"))
			this.entityData.set(DATA_ROTATION, compound.getFloat("DataROTATION"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		if (this.getDeltaMovement().length() > 0.1) this.setBallRotation((getBallRotation() + 18)%360);
		this.setHealth(this.getMaxHealth());
		this.refreshDimensions();
	}

	public static final ResourceKey<DamageType> TYPE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"));
	public static DamageSource getSource(Entity from, Entity mostRecentAttacker){
		if (mostRecentAttacker == null)
			return new DamageSource(
				from.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(TYPE), 
				from);
		return new DamageSource(
			from.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(TYPE), 
			from,
			mostRecentAttacker);
	}

	@Override
	public void push(Entity pEntity){
		super.push(pEntity);
		if (pEntity instanceof SliderBallEntity) return;
		if (pEntity instanceof SliderBallHugeEntity) return;
		if (pEntity == mostRecentAttacker) return;
		if (pEntity instanceof LivingEntity _entity && !_entity.level().isClientSide() && this.getDeltaMovement().length() > 0.1){
			_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1));
			_entity.hurt(getSource(this, this.mostRecentAttacker), (float)this.getDeltaMovement().length() * 10f);
		}
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.updateSwingTime();
	}

	@Override
	public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand){
		ItemStack item = pPlayer.getMainHandItem();
		if (item.isEmpty()){
			if (this.level() instanceof ServerLevel _level){
				ItemEntity entityToSpawn = new ItemEntity(_level, this.getX(), this.getY(), this.getZ(), 
					new ItemStack(CaerulaArborModItems.ITEM_SLIDER_BALL_HUGE.get()));
				entityToSpawn.setPickUpDelay(10);
				_level.addFreshEntity(entityToSpawn);
			}
			this.discard();
			return InteractionResult.SUCCESS;
		}
		return super.mobInteract(pPlayer, pHand);
	}

	@Override
	public void actuallyHurt(DamageSource pSource, float pAmount){
		Entity sourceEntity = pSource.getEntity();
		if (sourceEntity == null) return;
		this.mostRecentAttacker = sourceEntity;
		float power = (float)Mth.clamp(Math.sqrt(0.5 * (double) pAmount), 0, 10);
		Vec3 delta = sourceEntity.position().vectorTo(this.position()).normalize().scale(power);
		double sDy = Mth.clamp(sourceEntity.getDeltaMovement().y, -5, 5);
		this.push(delta.x, delta.y + sDy * 0.85, delta.z);
	}

	public static void init() {
	}

	public float getBallRotation(){
		return this.entityData.get(DATA_ROTATION);
	}
	public void setBallRotation(float f){
		this.entityData.set(DATA_ROTATION, f);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 199);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 1);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.slider_ball.idle"));
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
		if (this.deathTime == 20) {
			this.remove(SliderBallHugeEntity.RemovalReason.KILLED);
			this.dropExperience();
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
		data.add(new AnimationController<>(this, "movement", 4, this::movementPredicate));
		data.add(new AnimationController<>(this, "procedure", 4, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
