
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

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.Goal;
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
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.procedures.InitChickenProcedure;
import net.apocalypse.careunite.procedures.GetChickenSizeProcedure;
import net.apocalypse.careunite.procedures.CreativeEggInteractProcedure;
import net.apocalypse.careunite.procedures.ChickenRipeProcedure;
import net.apocalypse.careunite.procedures.ChickenGrowUpProcedure;
import net.apocalypse.careunite.procedures.ChickenFallProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import javax.annotation.Nullable;

import java.util.EnumSet;

public class OceanizedChickenEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(OceanizedChickenEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(OceanizedChickenEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(OceanizedChickenEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_GROW_TIME = SynchedEntityData.defineId(OceanizedChickenEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_LAY_COOLDOWN = SynchedEntityData.defineId(OceanizedChickenEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_IS_CHILD = SynchedEntityData.defineId(OceanizedChickenEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_EGG_OFFSET = SynchedEntityData.defineId(OceanizedChickenEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_EGG_RATE = SynchedEntityData.defineId(OceanizedChickenEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public OceanizedChickenEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.OCEANIZED_CHICKEN.get(), world);
	}

	public OceanizedChickenEntity(EntityType<OceanizedChickenEntity> type, Level world) {
		super(type, world);
		xpReward = 3;
		setNoAi(false);
		setMaxUpStep(0.6f);
		this.moveControl = new FlyingMoveControl(this, 10, true);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "oceanized_chicken_adult");
		this.entityData.define(DATA_GROW_TIME, 10000);
		this.entityData.define(DATA_LAY_COOLDOWN, 1200);
		this.entityData.define(DATA_IS_CHILD, false);
		this.entityData.define(DATA_EGG_OFFSET, 4);
		this.entityData.define(DATA_EGG_RATE, 1000);
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
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 4;
			}

			@Override
			public boolean canUse() {
				double x = OceanizedChickenEntity.this.getX();
				double y = OceanizedChickenEntity.this.getY();
				double z = OceanizedChickenEntity.this.getZ();
				Entity entity = OceanizedChickenEntity.this;
				Level world = OceanizedChickenEntity.this.level();
				return super.canUse() && ChickenRipeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedChickenEntity.this.getX();
				double y = OceanizedChickenEntity.this.getY();
				double z = OceanizedChickenEntity.this.getZ();
				Entity entity = OceanizedChickenEntity.this;
				Level world = OceanizedChickenEntity.this.level();
				return super.canContinueToUse() && ChickenRipeProcedure.execute(entity);
			}

		});
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(3, new Goal() {
			{
				this.setFlags(EnumSet.of(Goal.Flag.MOVE));
			}

			public boolean canUse() {
				if (OceanizedChickenEntity.this.getTarget() != null && !OceanizedChickenEntity.this.getMoveControl().hasWanted()) {
					double x = OceanizedChickenEntity.this.getX();
					double y = OceanizedChickenEntity.this.getY();
					double z = OceanizedChickenEntity.this.getZ();
					Entity entity = OceanizedChickenEntity.this;
					Level world = OceanizedChickenEntity.this.level();
					return ChickenRipeProcedure.execute(entity);
				} else {
					return false;
				}
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedChickenEntity.this.getX();
				double y = OceanizedChickenEntity.this.getY();
				double z = OceanizedChickenEntity.this.getZ();
				Entity entity = OceanizedChickenEntity.this;
				Level world = OceanizedChickenEntity.this.level();
				return ChickenRipeProcedure.execute(entity) && OceanizedChickenEntity.this.getMoveControl().hasWanted() && OceanizedChickenEntity.this.getTarget() != null && OceanizedChickenEntity.this.getTarget().isAlive();
			}

			@Override
			public void start() {
				LivingEntity livingentity = OceanizedChickenEntity.this.getTarget();
				Vec3 vec3d = livingentity.getEyePosition(1);
				OceanizedChickenEntity.this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, 1.25);
			}

			@Override
			public void tick() {
				LivingEntity livingentity = OceanizedChickenEntity.this.getTarget();
				if (OceanizedChickenEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
					OceanizedChickenEntity.this.doHurtTarget(livingentity);
				} else {
					double d0 = OceanizedChickenEntity.this.distanceToSqr(livingentity);
					if (d0 < 16) {
						Vec3 vec3d = livingentity.getEyePosition(1);
						OceanizedChickenEntity.this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, 1.25);
					}
				}
			}
		});
		this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1, 20) {
			@Override
			protected Vec3 getPosition() {
				RandomSource random = OceanizedChickenEntity.this.getRandom();
				double dir_x = OceanizedChickenEntity.this.getX() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_y = OceanizedChickenEntity.this.getY() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_z = OceanizedChickenEntity.this.getZ() + ((random.nextFloat() * 2 - 1) * 16);
				return new Vec3(dir_x, dir_y, dir_z);
			}
		});
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	public double getPassengersRidingOffset() {
		return super.getPassengersRidingOffset() + -0.2;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.chicken.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.chicken.death"));
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
	public void die(DamageSource source) {
		super.die(source);
		ChickenFallProcedure.execute(this);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		InitChickenProcedure.execute(this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataGROW_TIME", this.entityData.get(DATA_GROW_TIME));
		compound.putInt("DataLAY_COOLDOWN", this.entityData.get(DATA_LAY_COOLDOWN));
		compound.putBoolean("DataIS_CHILD", this.entityData.get(DATA_IS_CHILD));
		compound.putInt("DataEGG_OFFSET", this.entityData.get(DATA_EGG_OFFSET));
		compound.putInt("DataEGG_RATE", this.entityData.get(DATA_EGG_RATE));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataGROW_TIME"))
			this.entityData.set(DATA_GROW_TIME, compound.getInt("DataGROW_TIME"));
		if (compound.contains("DataLAY_COOLDOWN"))
			this.entityData.set(DATA_LAY_COOLDOWN, compound.getInt("DataLAY_COOLDOWN"));
		if (compound.contains("DataIS_CHILD"))
			this.entityData.set(DATA_IS_CHILD, compound.getBoolean("DataIS_CHILD"));
		if (compound.contains("DataEGG_OFFSET"))
			this.entityData.set(DATA_EGG_OFFSET, compound.getInt("DataEGG_OFFSET"));
		if (compound.contains("DataEGG_RATE"))
			this.entityData.set(DATA_EGG_RATE, compound.getInt("DataEGG_RATE"));
	}

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
		return CreativeEggInteractProcedure.execute(entity, sourceentity);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		ChickenGrowUpProcedure.execute(this.level(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		Entity entity = this;
		Level world = this.level();
		double x = this.getX();
		double y = entity.getY();
		double z = entity.getZ();
		return super.getDimensions(p_33597_).scale((float) GetChickenSizeProcedure.execute(entity));
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 10);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 2);
		builder = builder.add(Attributes.FOLLOW_RANGE, 22);
		builder = builder.add(Attributes.FLYING_SPEED, 0.3);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_chicken.idle"));
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
		if (this.swinging && this.lastSwing + 10L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.oceanized_chicken.attack"));
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
			this.remove(OceanizedChickenEntity.RemovalReason.KILLED);
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
		data.add(new AnimationController<>(this, "movement", 1, this::movementPredicate));
		data.add(new AnimationController<>(this, "attacking", 1, this::attackingPredicate));
		data.add(new AnimationController<>(this, "procedure", 1, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
