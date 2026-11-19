
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

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
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
import net.minecraft.util.RandomSource;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.procedures.BellTicksProcedure;
import net.apocalypse.careunite.procedures.BellDoHurtTargetProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.AlchemyCreation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.CaerulaArborMod;

public class ShakingBellEntity extends AlchemyCreation implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(ShakingBellEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(ShakingBellEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(ShakingBellEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_ATTACK_P = SynchedEntityData.defineId(ShakingBellEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_ATTACK_DURATION = SynchedEntityData.defineId(ShakingBellEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public ShakingBellEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.SHAKING_BELL.get(), world);
	}

	public ShakingBellEntity(EntityType<ShakingBellEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
		setMaxUpStep(0.6f);
		setPersistenceRequired();
		this.moveControl = new FlyingMoveControl(this, 10, true);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "shaking_bell");
		this.entityData.define(DATA_ATTACK_P, 1);
		this.entityData.define(DATA_ATTACK_DURATION, 0);
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public String getTexture() {
		return this.entityData.get(TEXTURE);
	}

	public void setStunned(boolean st){
		if (st) this.setAnimation("drop");
		else this.setAnimation("revive");
		this.setNoGravity(!st);
		super.setStunned(st);
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
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.4, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 4;
			}
			@Override
			public boolean canUse(){
				return super.canUse() && !ShakingBellEntity.this.isStunned();
			}
		});
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1, 20) {
			@Override
			protected Vec3 getPosition() {
				RandomSource random = ShakingBellEntity.this.getRandom();
				double dir_x = ShakingBellEntity.this.getX() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_y = ShakingBellEntity.this.getY() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_z = ShakingBellEntity.this.getZ() + ((random.nextFloat() * 2 - 1) * 16);
				return new Vec3(dir_x, dir_y, dir_z);
			}
			@Override
			public boolean canUse(){
				return super.canUse() && !ShakingBellEntity.this.isStunned();
			}
		});
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
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

	@Override
	public boolean hurt(DamageSource source, float amount) {
		
		if (source.is(DamageTypes.FALL))
			return false;
		return super.hurt(source, amount);
	}

	@Override
	public boolean doHurtTarget(Entity pEntity){
		BellDoHurtTargetProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), pEntity, this);
		return true;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataATTACK_P", this.entityData.get(DATA_ATTACK_P));
		compound.putInt("DataATTACK_DURATION", this.entityData.get(DATA_ATTACK_DURATION));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataATTACK_P"))
			this.entityData.set(DATA_ATTACK_P, compound.getInt("DataATTACK_P"));
		if (compound.contains("DataATTACK_DURATION"))
			this.entityData.set(DATA_ATTACK_DURATION, compound.getInt("DataATTACK_DURATION"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		BellTicksProcedure.execute(this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.27);
		builder = builder.add(Attributes.MAX_HEALTH, 24);
		builder = builder.add(Attributes.ARMOR, 7);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.64);
		builder = builder.add(Attributes.FLYING_SPEED, 0.27);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if (this.isStunned()) return event.setAndContinue(RawAnimation.begin().thenLoop("is_item"));
			if (entityData.get(DATA_ATTACK_DURATION) <= 0 || entityData.get(DATA_ATTACK_P) != 3) return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
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
		if (this.swinging && this.lastSwing + 17L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.entityData.get(DATA_ATTACK_DURATION) > 0 && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			int p = entityData.get(DATA_ATTACK_P);
			CaerulaArborMod.LOGGER.info("atk p on anim:{}",p);
			if (p == 3) return event.setAndContinue(RawAnimation.begin().thenPlay("ring"));
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
		++this.deathTime;
		if (this.deathTime == 2) {
			this.setBlockorDrop(Blocks.BELL, Blocks.BELL);
			this.remove(ShakingBellEntity.RemovalReason.KILLED);
			this.dropExperience();
		}
	}

	public void setBlockorDrop(Block block, ItemLike item){
    	BlockPos p = this.blockPosition();
    	if (this.level().isClientSide()) return;
    	if (this.level().getLevelData().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && this.level().getBlockState(p).canBeReplaced()) {
			this.level().setBlock(p, block.defaultBlockState(), 3);
		} else {
			this.spawnAtLocation(new ItemStack(item));
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
