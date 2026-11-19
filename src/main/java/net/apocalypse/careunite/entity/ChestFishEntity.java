
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

import net.minecraft.world.level.levelgen.Heightmap;
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
import net.minecraft.world.entity.SpawnPlacements;
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
import net.minecraft.world.Difficulty;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.UnKneeledProcedure;
import net.apocalypse.careunite.procedures.MobAliveProcedure;
import net.apocalypse.careunite.procedures.GrandChestAvdProcedure;
import net.apocalypse.careunite.procedures.DirectionInitProcedure;
import net.apocalypse.careunite.procedures.ChestStartProcedure;
import net.apocalypse.careunite.procedures.ChestInitProcedure;
import net.apocalypse.careunite.procedures.MegachestDoHurtTargetProcedure;

import net.apocalypse.careunite.init.CaerulaArborModEntities;

import javax.annotation.Nullable;

public class ChestFishEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(ChestFishEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(ChestFishEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(ChestFishEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Boolean> DATA_release = SynchedEntityData.defineId(ChestFishEntity.class, EntityDataSerializers.BOOLEAN);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public ChestFishEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.CHEST_FISH.get(), world);
	}

	public ChestFishEntity(EntityType<ChestFishEntity> type, Level world) {
		super(type, world);
		xpReward = 16;
		setNoAi(false);
		setMaxUpStep(0.6f);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "chest_fish");
		this.entityData.define(DATA_release, false);
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public String getTexture() {
		return this.entityData.get(TEXTURE);
	}

	@Override
	public boolean canCollideWith(Entity entity) {
		return true;
	}

	@Override
	public boolean canBeCollidedWith() {
		Entity entity = this;
		Level world = entity.level();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		return MobAliveProcedure.execute(entity);
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
				double x = ChestFishEntity.this.getX();
				double y = ChestFishEntity.this.getY();
				double z = ChestFishEntity.this.getZ();
				Entity entity = ChestFishEntity.this;
				Level world = ChestFishEntity.this.level();
				return super.canUse() && UnKneeledProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = ChestFishEntity.this.getX();
				double y = ChestFishEntity.this.getY();
				double z = ChestFishEntity.this.getZ();
				Entity entity = ChestFishEntity.this;
				Level world = ChestFishEntity.this.level();
				return super.canContinueToUse() && UnKneeledProcedure.execute(entity);
			}

		});
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = ChestFishEntity.this.getX();
				double y = ChestFishEntity.this.getY();
				double z = ChestFishEntity.this.getZ();
				Entity entity = ChestFishEntity.this;
				Level world = ChestFishEntity.this.level();
				return super.canUse() && UnKneeledProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = ChestFishEntity.this.getX();
				double y = ChestFishEntity.this.getY();
				double z = ChestFishEntity.this.getZ();
				Entity entity = ChestFishEntity.this;
				Level world = ChestFishEntity.this.level();
				return super.canContinueToUse() && UnKneeledProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = ChestFishEntity.this.getX();
				double y = ChestFishEntity.this.getY();
				double z = ChestFishEntity.this.getZ();
				Entity entity = ChestFishEntity.this;
				Level world = ChestFishEntity.this.level();
				return super.canUse() && UnKneeledProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = ChestFishEntity.this.getX();
				double y = ChestFishEntity.this.getY();
				double z = ChestFishEntity.this.getZ();
				Entity entity = ChestFishEntity.this;
				Level world = ChestFishEntity.this.level();
				return super.canContinueToUse() && UnKneeledProcedure.execute(entity);
			}
		});
	}

	@Override
	public MobType getMobType() {
		return MobType.WATER;
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
		boolean flag = super.hurt(source, amount);
		if (flag) ChestStartProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		return flag;
	}

	@Override
	public boolean doHurtTarget(Entity pEntity){
		MegachestDoHurtTargetProcedure.execute(this.level(), pEntity, this);
		return true;
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		GrandChestAvdProcedure.execute(this.level(), this, source.getEntity());
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		DirectionInitProcedure.execute(this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putBoolean("Datarelease", this.entityData.get(DATA_release));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("Datarelease"))
			this.entityData.set(DATA_release, compound.getBoolean("Datarelease"));
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
		return ChestStartProcedure.execute(world, x, y, z, entity);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		ChestInitProcedure.execute(this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	public static void init() {
		SpawnPlacements.register(CaerulaArborModEntities.CHEST_FISH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos, random) -> (world.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(world, pos, random) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 120);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 12);
		builder = builder.add(Attributes.FOLLOW_RANGE, 19);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.85);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.chest_fish.die"));
		}
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))

			&& this.entityData.get(DATA_release)) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.chest_fish.move"));
			}
			if (this.isShiftKeyDown()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.chest_fish.chest"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.chest_fish.idle"));
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
		if (this.swinging && this.lastSwing + 20L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.chest_fish.attack"));
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
			this.remove(ChestFishEntity.RemovalReason.KILLED);
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
		data.add(new AnimationController<>(this, "movement", 3, this::movementPredicate));
		data.add(new AnimationController<>(this, "attacking", 3, this::attackingPredicate));
		data.add(new AnimationController<>(this, "procedure", 3, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
