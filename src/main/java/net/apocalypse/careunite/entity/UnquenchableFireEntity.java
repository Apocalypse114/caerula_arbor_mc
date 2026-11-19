
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
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.apocalypse.careunite.procedures.ValerioFireExplodeProcedure;
import net.apocalypse.careunite.procedures.AllAttackFireProcedure;

public class UnquenchableFireEntity extends PathfinderMob implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(UnquenchableFireEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(UnquenchableFireEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(UnquenchableFireEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_TIME = SynchedEntityData.defineId(UnquenchableFireEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public UUID ownerUUID = null;
	public UUID linkedUUID = null;
	public Entity linkedEntity = null;

	public UnquenchableFireEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.UNQUENCHABLE_FIRE.get(), world);
	}

	public void setOwner(Entity owner){
		this.ownerUUID = owner.getUUID();
	}

	public void setLinked(Entity linked){
		this.linkedEntity = linked;
		this.linkedUUID = linked.getUUID();
	}

	public UnquenchableFireEntity(EntityType<UnquenchableFireEntity> type, Level world) {
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
		this.entityData.define(TEXTURE, "unquenchable_fire");
		this.entityData.define(DATA_TIME, 0);
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
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataTIME", this.entityData.get(DATA_TIME));
		if (ownerUUID != null) compound.putUUID("owner", ownerUUID);
		if (linkedUUID != null) compound.putUUID("linked", linkedUUID);
 	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataTIME"))
			this.entityData.set(DATA_TIME, compound.getInt("DataTIME"));
		if (compound.contains("owner"))
			ownerUUID = compound.getUUID("owner");
		if (compound.contains("linked"))
			ownerUUID = compound.getUUID("linked");
	}

	public int getTime(){
		return entityData.get(DATA_TIME);
	}

	public float getHealth(){
		if (getTime() < 56) return 20;
		return super.getHealth();
	}

	public boolean hurt(DamageSource pSource, float pAmount){
		if (getTime() <= 56) return false;
		return super.hurt(pSource, pAmount);
	}

	public boolean canBeSeenAsEnemy(){
		if (getTime() < 56) return false;
		return super.canBeSeenAsEnemy();
	}

	@Override
    public boolean isPushable() {
        return getTime() >= 56;
    }
    @Override
    protected void doPush(Entity entityIn) {
    	if (getTime() >= 56) super.doPush(entityIn);
    }
    @Override
    protected void pushEntities() {
    	if (getTime() > 0) super.pushEntities();
    }

	@Override
	public void baseTick() {
		super.baseTick();
		int t = entityData.get(DATA_TIME);
		entityData.set(DATA_TIME, t+1);
		if (this.level() instanceof ServerLevel sss){
			if (linkedEntity == null && linkedUUID != null) linkedEntity = sss.getEntity(linkedUUID);
			if (linkedEntity != null && linkedEntity.isAlive()) this.teleportTo(linkedEntity.getX(), linkedEntity.getY(), linkedEntity.getZ());
			if (t == 46){
				this.setAnimation("attack");
			}
			if (t == 51 && ownerUUID != null){
 				ValerioFireExplodeProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this, sss.getEntity(ownerUUID));
			}
			if (t == 56){
				if (linkedEntity != null && linkedEntity.isAlive()) this.discard();
				else{
					this.setAnimation("merge");
					if (ownerUUID != null) AllAttackFireProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this, sss.getEntity(ownerUUID));
				}
			}
		}
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
		builder = builder.add(Attributes.MAX_HEALTH, 30);
		builder = builder.add(Attributes.ARMOR, 3);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 1);
		builder = builder.add(Attributes.FOLLOW_RANGE, 8);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("die"));
			}
			if (getTime() < 56) return event.setAndContinue(RawAnimation.begin().thenLoop("hide"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("keep"));
		}
		return PlayState.STOP;
	}

	private PlayState signAnim(AnimationState event){
		return event.setAndContinue(RawAnimation.begin().thenLoop("sign"));
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
		if (this.deathTime == 15) {
			this.remove(UnquenchableFireEntity.RemovalReason.KILLED);
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
		data.add(new AnimationController<>(this, "procedure", 1, this::procedurePredicate));
		data.add(new AnimationController<>(this, "sign", 1, this::signAnim));

	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
