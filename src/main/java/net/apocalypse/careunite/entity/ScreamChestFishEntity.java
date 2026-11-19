
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

import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.UnKneeledProcedure;
import net.apocalypse.careunite.procedures.ScreamInitProcedure;
import net.apocalypse.careunite.procedures.ScreamChestTickProcedure;
import net.apocalypse.careunite.procedures.ScreamChestStartProcedure;
import net.apocalypse.careunite.procedures.GrandMEgaChestAdvProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import javax.annotation.Nullable;

public class ScreamChestFishEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(ScreamChestFishEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(ScreamChestFishEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(ScreamChestFishEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Boolean> DATA_release = SynchedEntityData.defineId(ScreamChestFishEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_SCREAM_TICK = SynchedEntityData.defineId(ScreamChestFishEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public ScreamChestFishEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.SCREAM_CHEST_FISH.get(), world);
	}

	public ScreamChestFishEntity(EntityType<ScreamChestFishEntity> type, Level world) {
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
		this.entityData.define(TEXTURE, "scream_chest_fish");
		this.entityData.define(DATA_release, false);
		this.entityData.define(DATA_SCREAM_TICK, 201);
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
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, true) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 3.24;
			}

			@Override
			public boolean canUse() {
				double x = ScreamChestFishEntity.this.getX();
				double y = ScreamChestFishEntity.this.getY();
				double z = ScreamChestFishEntity.this.getZ();
				ScreamChestFishEntity entity = ScreamChestFishEntity.this;
				Level world = ScreamChestFishEntity.this.level();
				return super.canUse() && UnKneeledProcedure.execute(entity) && !entity.isScreaming();
			}

			@Override
			public boolean canContinueToUse() {
				double x = ScreamChestFishEntity.this.getX();
				double y = ScreamChestFishEntity.this.getY();
				double z = ScreamChestFishEntity.this.getZ();
				ScreamChestFishEntity entity = ScreamChestFishEntity.this;
				Level world = ScreamChestFishEntity.this.level();
				return super.canContinueToUse() && UnKneeledProcedure.execute(entity) && !entity.isScreaming();
			}

		});
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = ScreamChestFishEntity.this.getX();
				double y = ScreamChestFishEntity.this.getY();
				double z = ScreamChestFishEntity.this.getZ();
				ScreamChestFishEntity entity = ScreamChestFishEntity.this;
				Level world = ScreamChestFishEntity.this.level();
				return super.canUse() && UnKneeledProcedure.execute(entity) && !entity.isScreaming();
			}

			@Override
			public boolean canContinueToUse() {
				double x = ScreamChestFishEntity.this.getX();
				double y = ScreamChestFishEntity.this.getY();
				double z = ScreamChestFishEntity.this.getZ();
				ScreamChestFishEntity entity = ScreamChestFishEntity.this;
				Level world = ScreamChestFishEntity.this.level();
				return super.canContinueToUse() && UnKneeledProcedure.execute(entity) && !entity.isScreaming();
			}
		});
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = ScreamChestFishEntity.this.getX();
				double y = ScreamChestFishEntity.this.getY();
				double z = ScreamChestFishEntity.this.getZ();
				Entity entity = ScreamChestFishEntity.this;
				Level world = ScreamChestFishEntity.this.level();
				return super.canUse() && UnKneeledProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = ScreamChestFishEntity.this.getX();
				double y = ScreamChestFishEntity.this.getY();
				double z = ScreamChestFishEntity.this.getZ();
				Entity entity = ScreamChestFishEntity.this;
				Level world = ScreamChestFishEntity.this.level();
				return super.canContinueToUse() && UnKneeledProcedure.execute(entity);
			}
		});
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
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.armor_stand.hit"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.armor_stand.break"));
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.FALL))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		if (isScreaming())
			return super.hurt(source, amount * 0.5f);
		boolean flag = super.hurt(source, amount);
		if (flag) ScreamChestStartProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		return flag;
	}

	@Override
	public boolean canCollideWith(Entity entity) {
		return true;
	}

	@Override
	public boolean canBeCollidedWith() {
		return this.isAlive();
	}


	@Override
	public void die(DamageSource source) {
		super.die(source);
		GrandMEgaChestAdvProcedure.execute(this.level(), this, source.getEntity());
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		ScreamInitProcedure.execute(this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putBoolean("Datarelease", this.entityData.get(DATA_release));
		compound.putInt("DataSCREAM_TICK", this.entityData.get(DATA_SCREAM_TICK));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("Datarelease"))
			this.entityData.set(DATA_release, compound.getBoolean("Datarelease"));
		if (compound.contains("DataSCREAM_TICK"))
			this.entityData.set(DATA_SCREAM_TICK, compound.getInt("DataSCREAM_TICK"));
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
		return ScreamChestStartProcedure.execute(world, x, y, z, entity);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		ScreamChestTickProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
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
		builder = builder.add(Attributes.MAX_HEALTH, 40);
		builder = builder.add(Attributes.ARMOR, 10);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.scream_chest_fish.die"));
		}
		if (this.animationprocedure.equals("empty")) {
			if (this.isScreaming()) return event.setAndContinue(RawAnimation.begin().thenLoop("animation.scream_chest_fish.scream"));
			if (this.isShiftKeyDown()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.scream_chest_fish.chest"));
			}
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.1F && event.getLimbSwingAmount() < 0.1F))
			&& this.entityData.get(DATA_release)
) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.scream_chest_fish.move"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.scream_chest_fish.idle"));
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
		if (this.swinging && this.lastSwing + 12L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.scream_chest_fish.attack"));
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
		if (this.deathTime == 15) {
			this.remove(ScreamChestFishEntity.RemovalReason.KILLED);
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
		data.add(new AnimationController<>(this, "movement", 0, this::movementPredicate));
		data.add(new AnimationController<>(this, "attacking", 0, this::attackingPredicate));
		data.add(new AnimationController<>(this, "procedure", 0, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	private boolean isScreaming(){
		if (!this.entityData.get(DATA_release)) return false;
		return this.entityData.get(DATA_SCREAM_TICK) > 0;
	}
}
