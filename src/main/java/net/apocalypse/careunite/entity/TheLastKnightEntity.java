
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
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
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
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.LastKnightTickProcedure;
import net.apocalypse.careunite.procedures.LastKnightFreezeProcedure;
import net.apocalypse.careunite.procedures.IsLastKnightDurativeProcedure;
import net.apocalypse.careunite.procedures.InitLastKnightProcedure;
import net.apocalypse.careunite.procedures.CallMyHorseProcedure;
import net.apocalypse.careunite.procedures.LastKnightDoHurtTargetProcedure;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import javax.annotation.Nullable;

import java.util.List;

public class TheLastKnightEntity extends Animal implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(TheLastKnightEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(TheLastKnightEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(TheLastKnightEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_duration = SynchedEntityData.defineId(TheLastKnightEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_skillp = SynchedEntityData.defineId(TheLastKnightEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.WHITE, ServerBossEvent.BossBarOverlay.NOTCHED_6);

	public TheLastKnightEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.THE_LAST_KNIGHT.get(), world);
	}

	public TheLastKnightEntity(EntityType<TheLastKnightEntity> type, Level world) {
		super(type, world);
		xpReward = 64;
		setNoAi(false);
		setMaxUpStep(1.25f);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "last_knight");
		this.entityData.define(DATA_duration, 0);
		this.entityData.define(DATA_skillp, 200);
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
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 36;
			}

			@Override
			public boolean canUse() {
				double x = TheLastKnightEntity.this.getX();
				double y = TheLastKnightEntity.this.getY();
				double z = TheLastKnightEntity.this.getZ();
				Entity entity = TheLastKnightEntity.this;
				Level world = TheLastKnightEntity.this.level();
				return super.canUse() && IsLastKnightDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = TheLastKnightEntity.this.getX();
				double y = TheLastKnightEntity.this.getY();
				double z = TheLastKnightEntity.this.getZ();
				Entity entity = TheLastKnightEntity.this;
				Level world = TheLastKnightEntity.this.level();
				return super.canContinueToUse() && IsLastKnightDurativeProcedure.execute(entity);
			}

		});
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Monster.class, true, false) {
			@Override
			public boolean canUse() {
				double x = TheLastKnightEntity.this.getX();
				double y = TheLastKnightEntity.this.getY();
				double z = TheLastKnightEntity.this.getZ();
				Entity entity = TheLastKnightEntity.this;
				Level world = TheLastKnightEntity.this.level();
				return super.canUse() && IsLastKnightDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = TheLastKnightEntity.this.getX();
				double y = TheLastKnightEntity.this.getY();
				double z = TheLastKnightEntity.this.getZ();
				Entity entity = TheLastKnightEntity.this;
				Level world = TheLastKnightEntity.this.level();
				return super.canContinueToUse() && IsLastKnightDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = TheLastKnightEntity.this.getX();
				double y = TheLastKnightEntity.this.getY();
				double z = TheLastKnightEntity.this.getZ();
				Entity entity = TheLastKnightEntity.this;
				Level world = TheLastKnightEntity.this.level();
				return super.canUse() && IsLastKnightDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = TheLastKnightEntity.this.getX();
				double y = TheLastKnightEntity.this.getY();
				double z = TheLastKnightEntity.this.getZ();
				Entity entity = TheLastKnightEntity.this;
				Level world = TheLastKnightEntity.this.level();
				return super.canContinueToUse() && IsLastKnightDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = TheLastKnightEntity.this.getX();
				double y = TheLastKnightEntity.this.getY();
				double z = TheLastKnightEntity.this.getZ();
				Entity entity = TheLastKnightEntity.this;
				Level world = TheLastKnightEntity.this.level();
				return super.canUse() && IsLastKnightDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = TheLastKnightEntity.this.getX();
				double y = TheLastKnightEntity.this.getY();
				double z = TheLastKnightEntity.this.getZ();
				Entity entity = TheLastKnightEntity.this;
				Level world = TheLastKnightEntity.this.level();
				return super.canContinueToUse() && IsLastKnightDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(6, new FloatGoal(this));
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
		super.dropCustomDeathLoot(source, looting, recentlyHitIn);
		this.spawnAtLocation(new ItemStack(CaerulaArborModItems.KNIGHT_CORPSE.get()));
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:last_knight_hit"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:last_knight_hit"));
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		LastKnightFreezeProcedure.execute(this.level(), this, source.getEntity());
		if (source.is(DamageTypes.IN_FIRE))
			return false;
		if (source.getDirectEntity() instanceof ThrownPotion || source.getDirectEntity() instanceof AreaEffectCloud)
			return false;
		if (source.is(DamageTypes.CACTUS))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		return super.hurt(source, amount);
	}

	public int lastAttackTick = 0;
	@Override
	public boolean doHurtTarget(Entity pEntity){
		if (this.tickCount > lastAttackTick && this.tickCount < lastAttackTick + 16) return false;
		lastAttackTick = this.tickCount;
		LastKnightDoHurtTargetProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), pEntity, this);
		return true;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		InitLastKnightProcedure.execute(this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("Dataduration", this.entityData.get(DATA_duration));
		compound.putInt("Dataskillp", this.entityData.get(DATA_skillp));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("Dataduration"))
			this.entityData.set(DATA_duration, compound.getInt("Dataduration"));
		if (compound.contains("Dataskillp"))
			this.entityData.set(DATA_skillp, compound.getInt("Dataskillp"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		LastKnightTickProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
		TheLastKnightEntity retval = CaerulaArborModEntities.THE_LAST_KNIGHT.get().create(serverWorld);
		retval.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(retval.blockPosition()), MobSpawnType.BREEDING, null, null);
		return retval;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return List.of().contains(stack.getItem());
	}

	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	@Override
	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer player) {
		super.stopSeenByPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	@Override
	public void customServerAiStep() {
		super.customServerAiStep();
		this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 400);
		builder = builder.add(Attributes.ARMOR, 24);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 20);
		builder = builder.add(Attributes.FOLLOW_RANGE, 36);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 10);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.last_knight.die"));
		}
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))

			) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.last_knight.move"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.last_knight.idle"));
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
		if (this.swinging && this.lastSwing + 19L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			if (this.level().getRandom().nextDouble() < 0.5)
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.last_knight.attack"));
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.last_knight.attack1"));
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
		if (this.deathTime == 35) {
			CallMyHorseProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);

			this.remove(TheLastKnightEntity.RemovalReason.KILLED);
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
		data.add(new AnimationController<>(this, "attacking", 0, this::attackingPredicate));
		data.add(new AnimationController<>(this, "procedure", 0, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
