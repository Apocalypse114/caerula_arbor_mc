
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
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.SpawnGroupData;
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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.UlpiansSkillsProcedure;
import net.apocalypse.careunite.procedures.UlpiansHealSelfProcedure;
import net.apocalypse.careunite.procedures.UlpiansBonusProcedure;
import net.apocalypse.careunite.procedures.IsUlpuansDurativeProcedure;
import net.apocalypse.careunite.procedures.HunterInitNewProcedure;
import net.apocalypse.careunite.procedures.AbyssalHunterDoHurtTargetProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import javax.annotation.Nullable;

import java.util.List;

public class UlpiansEntity extends TamableAnimal implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(UlpiansEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(UlpiansEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(UlpiansEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_duration = SynchedEntityData.defineId(UlpiansEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_skillp1 = SynchedEntityData.defineId(UlpiansEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_skillp2 = SynchedEntityData.defineId(UlpiansEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_bonus = SynchedEntityData.defineId(UlpiansEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public UlpiansEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.ULPIANS.get(), world);
	}

	public UlpiansEntity(EntityType<UlpiansEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
		setMaxUpStep(1f);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "hunter_ulpians");
		this.entityData.define(DATA_duration, 0);
		this.entityData.define(DATA_skillp1, 80);
		this.entityData.define(DATA_skillp2, 160);
		this.entityData.define(DATA_bonus, 0);
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
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.1, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 9;
			}

			@Override
			public boolean canUse() {
				double x = UlpiansEntity.this.getX();
				double y = UlpiansEntity.this.getY();
				double z = UlpiansEntity.this.getZ();
				Entity entity = UlpiansEntity.this;
				Level world = UlpiansEntity.this.level();
				return super.canUse() && IsUlpuansDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = UlpiansEntity.this.getX();
				double y = UlpiansEntity.this.getY();
				double z = UlpiansEntity.this.getZ();
				Entity entity = UlpiansEntity.this;
				Level world = UlpiansEntity.this.level();
				return super.canContinueToUse() && IsUlpuansDurativeProcedure.execute(entity);
			}

		});
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Monster.class, true, false));
		this.goalSelector.addGoal(4, new OpenDoorGoal(this, false));
		this.goalSelector.addGoal(5, new OpenDoorGoal(this, true));
		this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = UlpiansEntity.this.getX();
				double y = UlpiansEntity.this.getY();
				double z = UlpiansEntity.this.getZ();
				Entity entity = UlpiansEntity.this;
				Level world = UlpiansEntity.this.level();
				return super.canUse() && IsUlpuansDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = UlpiansEntity.this.getX();
				double y = UlpiansEntity.this.getY();
				double z = UlpiansEntity.this.getZ();
				Entity entity = UlpiansEntity.this;
				Level world = UlpiansEntity.this.level();
				return super.canContinueToUse() && IsUlpuansDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = UlpiansEntity.this.getX();
				double y = UlpiansEntity.this.getY();
				double z = UlpiansEntity.this.getZ();
				Entity entity = UlpiansEntity.this;
				Level world = UlpiansEntity.this.level();
				return super.canUse() && IsUlpuansDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = UlpiansEntity.this.getX();
				double y = UlpiansEntity.this.getY();
				double z = UlpiansEntity.this.getZ();
				Entity entity = UlpiansEntity.this;
				Level world = UlpiansEntity.this.level();
				return super.canContinueToUse() && IsUlpuansDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(8, new FloatGoal(this));
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
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:ulpians_hit"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:ulpians_die"));
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		UlpiansHealSelfProcedure.execute(this);
		if (source.is(DamageTypes.FALL))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		return super.hurt(source, amount);
	}

	@Override
	public boolean doHurtTarget(Entity pEntity){
		AbyssalHunterDoHurtTargetProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), pEntity, this);
		return true;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		HunterInitNewProcedure.execute(this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("Dataduration", this.entityData.get(DATA_duration));
		compound.putInt("Dataskillp1", this.entityData.get(DATA_skillp1));
		compound.putInt("Dataskillp2", this.entityData.get(DATA_skillp2));
		compound.putInt("Databonus", this.entityData.get(DATA_bonus));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("Dataduration"))
			this.entityData.set(DATA_duration, compound.getInt("Dataduration"));
		if (compound.contains("Dataskillp1"))
			this.entityData.set(DATA_skillp1, compound.getInt("Dataskillp1"));
		if (compound.contains("Dataskillp2"))
			this.entityData.set(DATA_skillp2, compound.getInt("Dataskillp2"));
		if (compound.contains("Databonus"))
			this.entityData.set(DATA_bonus, compound.getInt("Databonus"));
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
		return retval;
	}

	@Override
	public void awardKillScore(Entity entity, int score, DamageSource damageSource) {
		super.awardKillScore(entity, score, damageSource);
		UlpiansBonusProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		UlpiansSkillsProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
		UlpiansEntity retval = CaerulaArborModEntities.ULPIANS.get().create(serverWorld);
		retval.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(retval.blockPosition()), MobSpawnType.BREEDING, null, null);
		return retval;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return List.of().contains(stack.getItem());
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.18);
		builder = builder.add(Attributes.MAX_HEALTH, 430);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 55);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 5);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))

			) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.ulpians.move"));
			}
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.ulpians.die"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.ulpians.idle"));
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
		if (this.swinging && this.lastSwing + 30L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.ulpians.attack"));
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
		if (this.deathTime >= 20) {
			SkadiEntity.generateTransporter(this, 1400);
			this.remove(UlpiansEntity.RemovalReason.KILLED);
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
}
