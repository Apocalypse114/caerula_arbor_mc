
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

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

import net.apocalypse.careunite.procedures.SnifferSkillsProcedure;
import net.apocalypse.careunite.procedures.IsSnifferDurativeProcedure;
import net.apocalypse.careunite.procedures.GetEggNumProcedure;
import net.apocalypse.careunite.procedures.SnifferDoHurtTargetProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

public class OceanizedSnifferEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(OceanizedSnifferEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(OceanizedSnifferEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(OceanizedSnifferEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_duration = SynchedEntityData.defineId(OceanizedSnifferEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_skillp1 = SynchedEntityData.defineId(OceanizedSnifferEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_skillp2 = SynchedEntityData.defineId(OceanizedSnifferEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_dig_time = SynchedEntityData.defineId(OceanizedSnifferEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_phase = SynchedEntityData.defineId(OceanizedSnifferEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.BLUE, ServerBossEvent.BossBarOverlay.PROGRESS);

	public OceanizedSnifferEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.OCEANIZED_SNIFFER.get(), world);
	}

	public OceanizedSnifferEntity(EntityType<OceanizedSnifferEntity> type, Level world) {
		super(type, world);
		xpReward = 28;
		setNoAi(false);
		setMaxUpStep(1.25f);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "oceanized_sniffer");
		this.entityData.define(DATA_duration, 0);
		this.entityData.define(DATA_skillp1, 0);
		this.entityData.define(DATA_skillp2, 200);
		this.entityData.define(DATA_dig_time, 0);
		this.entityData.define(DATA_phase, 0);
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
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.5, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 12.25;
			}

			@Override
			public boolean canUse() {
				double x = OceanizedSnifferEntity.this.getX();
				double y = OceanizedSnifferEntity.this.getY();
				double z = OceanizedSnifferEntity.this.getZ();
				Entity entity = OceanizedSnifferEntity.this;
				Level world = OceanizedSnifferEntity.this.level();
				return super.canUse() && IsSnifferDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedSnifferEntity.this.getX();
				double y = OceanizedSnifferEntity.this.getY();
				double z = OceanizedSnifferEntity.this.getZ();
				Entity entity = OceanizedSnifferEntity.this;
				Level world = OceanizedSnifferEntity.this.level();
				return super.canContinueToUse() && IsSnifferDurativeProcedure.execute(entity);
			}

		});
		this.goalSelector.addGoal(3, new TemptGoal(this, 1, Ingredient.of(Blocks.TORCHFLOWER.asItem()), false) {
			@Override
			public boolean canUse() {
				double x = OceanizedSnifferEntity.this.getX();
				double y = OceanizedSnifferEntity.this.getY();
				double z = OceanizedSnifferEntity.this.getZ();
				Entity entity = OceanizedSnifferEntity.this;
				Level world = OceanizedSnifferEntity.this.level();
				return super.canUse() && IsSnifferDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedSnifferEntity.this.getX();
				double y = OceanizedSnifferEntity.this.getY();
				double z = OceanizedSnifferEntity.this.getZ();
				Entity entity = OceanizedSnifferEntity.this;
				Level world = OceanizedSnifferEntity.this.level();
				return super.canContinueToUse() && IsSnifferDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = OceanizedSnifferEntity.this.getX();
				double y = OceanizedSnifferEntity.this.getY();
				double z = OceanizedSnifferEntity.this.getZ();
				Entity entity = OceanizedSnifferEntity.this;
				Level world = OceanizedSnifferEntity.this.level();
				return super.canUse() && IsSnifferDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedSnifferEntity.this.getX();
				double y = OceanizedSnifferEntity.this.getY();
				double z = OceanizedSnifferEntity.this.getZ();
				Entity entity = OceanizedSnifferEntity.this;
				Level world = OceanizedSnifferEntity.this.level();
				return super.canContinueToUse() && IsSnifferDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = OceanizedSnifferEntity.this.getX();
				double y = OceanizedSnifferEntity.this.getY();
				double z = OceanizedSnifferEntity.this.getZ();
				Entity entity = OceanizedSnifferEntity.this;
				Level world = OceanizedSnifferEntity.this.level();
				return super.canUse() && IsSnifferDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedSnifferEntity.this.getX();
				double y = OceanizedSnifferEntity.this.getY();
				double z = OceanizedSnifferEntity.this.getZ();
				Entity entity = OceanizedSnifferEntity.this;
				Level world = OceanizedSnifferEntity.this.level();
				return super.canContinueToUse() && IsSnifferDurativeProcedure.execute(entity);
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

	@Override
	public SoundEvent getAmbientSound() {
		return SoundEvents.SNIFFER_IDLE;
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.SNIFFER_STEP, 0.15f, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return SoundEvents.SNIFFER_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.SNIFFER_DEATH;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		int egg = GetEggNumProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ());
		if (source.is(DamageTypes.DROWN))
			return false;
		if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return super.hurt(source, amount);
		float rate = Math.max(1 - egg * 0.2f, 0.2f);
		int p = this.phase();
		if (p==1) rate *= 0.75f;
		else if (p==2) rate *= 0.5f;
		if (isDiging()) rate *= 0.5f;
		return super.hurt(source, amount * rate);
	}

	@Override
	public boolean doHurtTarget(Entity pEntity){
		SnifferDoHurtTargetProcedure.execute(this.level(), pEntity, this);
		return true;
	}

	@Override
	public void setHealth(float pHealth){
		float h = this.getMaxHealth();
		float ch = this.getHealth();
		float finalValue = pHealth;
		int p = phase();
		if (p <= 0 && ch > h * 0.67f) finalValue = Math.max(pHealth, h * 0.67f);
		else if (p <= 1 && ch > h * 0.33f) finalValue = Math.max(pHealth, h * 0.33f);
		super.setHealth(finalValue);
	}

	private int phase(){
		return this.entityData.get(DATA_phase);
	}

	private boolean isDiging(){
		return this.entityData.get(DATA_dig_time) > 0;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("Dataduration", this.entityData.get(DATA_duration));
		compound.putInt("Dataskillp1", this.entityData.get(DATA_skillp1));
		compound.putInt("Dataskillp2", this.entityData.get(DATA_skillp2));
		compound.putInt("Datadig_time", this.entityData.get(DATA_dig_time));
		compound.putInt("Dataphase", this.entityData.get(DATA_phase));
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
		if (compound.contains("Datadig_time"))
			this.entityData.set(DATA_dig_time, compound.getInt("Datadig_time"));
		if (compound.contains("Dataphase"))
			this.entityData.set(DATA_phase, compound.getInt("Dataphase"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		SnifferSkillsProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
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
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		this.setAnimation("animation.oceanized_sniffer.merge");
		this.entityData.set(DATA_duration, 40);
		AttributeInstance brr = this.getAttribute(CaerulaArborModAttributes.LIVING_BARRIER.get());
		if (brr != null) brr.setBaseValue((float)this.getMaxHealth());
		return retval;
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.16);
		builder = builder.add(Attributes.MAX_HEALTH, 72);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 7);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.oceanized_sniffer.die"));
		}
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.075F && event.getLimbSwingAmount() < 0.075F))
					&& !this.isAggressive()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_sniffer.move"));
			}
			if (this.isAggressive() && event.isMoving()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_sniffer.sprint"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_sniffer.idle"));
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
		if (this.swinging && this.lastSwing + 25L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.oceanized_sniffer.attack"));
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
		if (this.deathTime == 30) {
			this.remove(OceanizedSnifferEntity.RemovalReason.KILLED);
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
