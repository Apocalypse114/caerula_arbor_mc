
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.TapTapXantisProcedure;
import net.apocalypse.careunite.procedures.IsXantisTapativeProcedure;
import net.apocalypse.careunite.procedures.IsByApocataProcedure;
import net.apocalypse.careunite.procedures.AllAttackXantisProcedure;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import net.minecraft.sounds.SoundEvents;

public class XantisEntity extends TamableAnimal implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(XantisEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(XantisEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(XantisEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Boolean> DATA_NIUBI = SynchedEntityData.defineId(XantisEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_TAP_TICK = SynchedEntityData.defineId(XantisEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public XantisEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.XANTIS.get(), world);
	}

	public XantisEntity(EntityType<XantisEntity> type, Level world) {
		super(type, world);
		xpReward = 512;
		setNoAi(false);
		setMaxUpStep(1.25f);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "xantis");
		this.entityData.define(DATA_NIUBI, true);
		this.entityData.define(DATA_TAP_TICK, 0);
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
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 2.25;
			}
		});
		this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
		this.goalSelector.addGoal(3, new OwnerHurtByTargetGoal(this));
		this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1, (float) 3, (float) 24, false));
		this.targetSelector.addGoal(5, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(6, new FollowMobGoal(this, 1, (float) 16, (float) 12));
		this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = XantisEntity.this.getX();
				double y = XantisEntity.this.getY();
				double z = XantisEntity.this.getZ();
				Entity entity = XantisEntity.this;
				Level world = XantisEntity.this.level();
				return super.canUse() && IsXantisTapativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = XantisEntity.this.getX();
				double y = XantisEntity.this.getY();
				double z = XantisEntity.this.getZ();
				Entity entity = XantisEntity.this;
				Level world = XantisEntity.this.level();
				return super.canContinueToUse() && IsXantisTapativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = XantisEntity.this.getX();
				double y = XantisEntity.this.getY();
				double z = XantisEntity.this.getZ();
				Entity entity = XantisEntity.this;
				Level world = XantisEntity.this.level();
				return super.canUse() && IsXantisTapativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = XantisEntity.this.getX();
				double y = XantisEntity.this.getY();
				double z = XantisEntity.this.getZ();
				Entity entity = XantisEntity.this;
				Level world = XantisEntity.this.level();
				return super.canContinueToUse() && IsXantisTapativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(9, new FloatGoal(this));
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
	public SoundEvent getHurtSound(DamageSource ds) {
		return SoundEvents.AXOLOTL_ATTACK;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.AXOLOTL_DEATH;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (IsByApocataProcedure.execute(source.getEntity())){
            this.setNoNiubi();
            return super.hurt(source,114514);
        }
        if (this.isNiubi()) return super.hurt(source, 0.1F);
        return super.hurt(source, amount);
	}

	@Override
    protected void actuallyHurt(@NotNull DamageSource pDamageSource, float pDamageAmount){
    	if (!isNiubi()) super.actuallyHurt(pDamageSource, pDamageAmount);
        else super.actuallyHurt(pDamageSource, (float) Math.min(0.1, pDamageAmount));
    }

	@Override
    public void die(@NotNull DamageSource pSource){
        if (isNiubi()){
			this.hurt(this.level().damageSources().fellOutOfWorld(), 0.1F);
			return;
        };
        super.die(pSource);
    }

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putBoolean("DataNIUBI", this.entityData.get(DATA_NIUBI));
		compound.putInt("DataTAP_TICK", this.entityData.get(DATA_TAP_TICK));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataNIUBI"))
			this.entityData.set(DATA_NIUBI, compound.getBoolean("DataNIUBI"));
		if (compound.contains("DataTAP_TICK"))
			this.entityData.set(DATA_TAP_TICK, compound.getInt("DataTAP_TICK"));
	}

	@Override
	public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();
		Entity entity = this;
		Level world = this.level();
		return TapTapXantisProcedure.execute(world, x, y, z, entity, sourceentity);
	}

	@Override
	public void baseTick() {
		setNoAi(false);
		super.baseTick();
		AllAttackXantisProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		AttributeInstance MAX_H = this.getAttribute(Attributes.MAX_HEALTH);
        if (MAX_H != null && MAX_H.getValue() != 10){
        	MAX_H.setBaseValue(10);
        	MAX_H.removeModifiers();
        }
		this.refreshDimensions();
		if (this.getY() < -99) this.setPosRaw(this.getX(), 256, this.getZ());
	}

	public int MAX_DIST = 16777216;
    @Override
    public void setPos(double x, double y, double z){
        if (this.tickCount > 5 && this.distanceToSqr(x,y,z) > MAX_DIST) return;
        super.setPos(x,y,z);
    }
    @Override
    public void moveTo(double x, double y, double z, float pYRot, float pXRot){
        if (this.tickCount > 5 && this.distanceToSqr(x,y,z) > MAX_DIST) return;
        super.moveTo(x,y,z,pYRot,pXRot);
    }

	@Override
    public void setHealth(float pHealth){
        if (isNiubi() && pHealth < 1) {
            if(this.getHealth() > 1) super.setHealth(1);
            else super.setHealth(this.getMaxHealth());
            return;
        }
        super.setHealth(pHealth);
    }

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
		XantisEntity retval = CaerulaArborModEntities.XANTIS.get().create(serverWorld);
		retval.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(retval.blockPosition()), MobSpawnType.BREEDING, null, null);
		return retval;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return List.of(CaerulaArborModItems.APOCALYPSE.get()).contains(stack.getItem());
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.15);
		builder = builder.add(Attributes.MAX_HEALTH, 10);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))

			) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.xantis.move"));
			}
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.xantis.die"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.xantis.idle"));
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
		if (this.swinging && this.lastSwing + 9L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.xantis.attack"));
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
		if (isNiubi()){
            this.deathTime = 0;
            this.setHealth(10);
            return;
        }
		++this.deathTime;
		if (this.deathTime >= 19) {
			this.remove(XantisEntity.RemovalReason.KILLED);
			this.dropExperience();
		}
	}

	@Override
    public void remove(@NotNull RemovalReason pReason){
        if (isNiubi() && (pReason == RemovalReason.DISCARDED || pReason == RemovalReason.KILLED)){
            this.hurt(this.level().damageSources().fellOutOfWorld(), 0.1F);
            return;
        }
        super.remove(pReason);
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

	public boolean isNiubi(){return this.entityData.get(DATA_NIUBI);}
	public void setNoNiubi(){this.entityData.set(DATA_NIUBI, false);}
}
