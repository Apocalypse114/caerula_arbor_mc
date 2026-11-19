
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

import net.minecraft.world.level.Level;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.WhenNotSittingProcedure;
import net.apocalypse.careunite.procedures.SwitchSittingProcedure;
import net.apocalypse.careunite.procedures.DogStandupProcedure;
import net.apocalypse.careunite.procedures.DogSittingProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import java.util.List;

public class OceanizedDogEntity extends TamableAnimal implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(OceanizedDogEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(OceanizedDogEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(OceanizedDogEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Boolean> DATA_sitting = SynchedEntityData.defineId(OceanizedDogEntity.class, EntityDataSerializers.BOOLEAN);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public OceanizedDogEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.OCEANIZED_DOG.get(), world);
	}

	public OceanizedDogEntity(EntityType<OceanizedDogEntity> type, Level world) {
		super(type, world);
		xpReward = 4;
		setNoAi(false);
		setMaxUpStep(1f);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "oceanized_wolf_tamed");
		this.entityData.define(DATA_sitting, false);
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
		this.goalSelector.addGoal(1, new OwnerHurtByTargetGoal(this) {
			@Override
			public boolean canUse() {
				double x = OceanizedDogEntity.this.getX();
				double y = OceanizedDogEntity.this.getY();
				double z = OceanizedDogEntity.this.getZ();
				Entity entity = OceanizedDogEntity.this;
				Level world = OceanizedDogEntity.this.level();
				return super.canUse() && WhenNotSittingProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedDogEntity.this.getX();
				double y = OceanizedDogEntity.this.getY();
				double z = OceanizedDogEntity.this.getZ();
				Entity entity = OceanizedDogEntity.this;
				Level world = OceanizedDogEntity.this.level();
				return super.canContinueToUse() && WhenNotSittingProcedure.execute(entity);
			}
		});
		this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this) {
			@Override
			public boolean canUse() {
				double x = OceanizedDogEntity.this.getX();
				double y = OceanizedDogEntity.this.getY();
				double z = OceanizedDogEntity.this.getZ();
				Entity entity = OceanizedDogEntity.this;
				Level world = OceanizedDogEntity.this.level();
				return super.canUse() && WhenNotSittingProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedDogEntity.this.getX();
				double y = OceanizedDogEntity.this.getY();
				double z = OceanizedDogEntity.this.getZ();
				Entity entity = OceanizedDogEntity.this;
				Level world = OceanizedDogEntity.this.level();
				return super.canContinueToUse() && WhenNotSittingProcedure.execute(entity);
			}
		});
		this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 2.25;
			}
		});
		this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1, (float) 4, (float) 16, false) {
			@Override
			public boolean canUse() {
				double x = OceanizedDogEntity.this.getX();
				double y = OceanizedDogEntity.this.getY();
				double z = OceanizedDogEntity.this.getZ();
				Entity entity = OceanizedDogEntity.this;
				Level world = OceanizedDogEntity.this.level();
				return super.canUse() && WhenNotSittingProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedDogEntity.this.getX();
				double y = OceanizedDogEntity.this.getY();
				double z = OceanizedDogEntity.this.getZ();
				Entity entity = OceanizedDogEntity.this;
				Level world = OceanizedDogEntity.this.level();
				return super.canContinueToUse() && WhenNotSittingProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = OceanizedDogEntity.this.getX();
				double y = OceanizedDogEntity.this.getY();
				double z = OceanizedDogEntity.this.getZ();
				Entity entity = OceanizedDogEntity.this;
				Level world = OceanizedDogEntity.this.level();
				return super.canUse() && WhenNotSittingProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedDogEntity.this.getX();
				double y = OceanizedDogEntity.this.getY();
				double z = OceanizedDogEntity.this.getZ();
				Entity entity = OceanizedDogEntity.this;
				Level world = OceanizedDogEntity.this.level();
				return super.canContinueToUse() && WhenNotSittingProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(8, new FloatGoal(this));
	}

	@Override
	public MobType getMobType() {
		return MobType.WATER;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wolf.ambient"));
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wolf.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wolf.death"));
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		DogStandupProcedure.execute(this);
		if (source.is(DamageTypes.DROWN))
			return false;
		return super.hurt(source, amount);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putBoolean("Datasitting", this.entityData.get(DATA_sitting));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("Datasitting"))
			this.entityData.set(DATA_sitting, compound.getBoolean("Datasitting"));
	}

	@Override
	public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
		ItemStack itemstack = sourceentity.getItemInHand(hand);
		InteractionResult retval = InteractionResult.sidedSuccess(this.level().isClientSide());
		Item item = itemstack.getItem();
		if (itemstack.getItem() instanceof SpawnEggItem) {
			retval = super.mobInteract(sourceentity, hand);
		} else if (this.level().isClientSide()) {
			retval = (this.isTame() && this.isOwnedBy(sourceentity) || this.isFood(itemstack)) ? InteractionResult.sidedSuccess(this.level().isClientSide()) : InteractionResult.PASS;
		} else {
			if (this.isTame()) {
				if (this.isOwnedBy(sourceentity)) {
					if (item.isEdible() && this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
						this.usePlayerItem(sourceentity, hand, itemstack);
						this.heal((float) item.getFoodProperties().getNutrition());
						retval = InteractionResult.sidedSuccess(this.level().isClientSide());
					} else if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
						this.usePlayerItem(sourceentity, hand, itemstack);
						this.heal(4);
						retval = InteractionResult.sidedSuccess(this.level().isClientSide());
					} else {
						retval = super.mobInteract(sourceentity, hand);
					}
				}
			} else if (this.isFood(itemstack)) {
				this.usePlayerItem(sourceentity, hand, itemstack);
				if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, sourceentity)) {
					this.tame(sourceentity);
					this.level().broadcastEntityEvent(this, (byte) 7);
				} else {
					this.level().broadcastEntityEvent(this, (byte) 6);
				}
				this.setPersistenceRequired();
				retval = InteractionResult.sidedSuccess(this.level().isClientSide());
			} else {
				retval = super.mobInteract(sourceentity, hand);
				if (retval == InteractionResult.SUCCESS || retval == InteractionResult.CONSUME)
					this.setPersistenceRequired();
			}
		}
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();
		Entity entity = this;
		Level world = this.level();
		return SwitchSittingProcedure.execute(world, x, y, z, entity, sourceentity);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		DogSittingProcedure.execute(this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
		OceanizedDogEntity retval = CaerulaArborModEntities.OCEANIZED_DOG.get().create(serverWorld);
		retval.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(retval.blockPosition()), MobSpawnType.BREEDING, null, null);
		return retval;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return List.of(Items.BONE).contains(stack.getItem());
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.MAX_HEALTH, 85);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 9);
		builder = builder.add(Attributes.FOLLOW_RANGE, 14);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.33);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))

					&& !this.isAggressive()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_wolf.walk"));
			}
			if (this.isShiftKeyDown()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_wolf.sit"));
			}
			if (this.isAggressive() && event.isMoving()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_wolf.chase"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_wolf.idle"));
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
			this.remove(OceanizedDogEntity.RemovalReason.KILLED);
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
