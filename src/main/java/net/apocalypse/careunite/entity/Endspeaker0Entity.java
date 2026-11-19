
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.IsEndspeakerStartingProcedure;
import net.apocalypse.careunite.procedures.EndspeakerStartProcedure;
import net.apocalypse.careunite.procedures.EndspeakerReviveProcedure;
import net.apocalypse.careunite.procedures.Endspeaker0SkillsProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import javax.annotation.Nullable;

public class Endspeaker0Entity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(Endspeaker0Entity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(Endspeaker0Entity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(Endspeaker0Entity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Boolean> DATA_isEvolving = SynchedEntityData.defineId(Endspeaker0Entity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_EvolveTime = SynchedEntityData.defineId(Endspeaker0Entity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.BLUE, ServerBossEvent.BossBarOverlay.PROGRESS);

	public Endspeaker0Entity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.ENDSPEAKER_0.get(), world);
	}

	public Endspeaker0Entity(EntityType<Endspeaker0Entity> type, Level world) {
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
		this.entityData.define(TEXTURE, "endspeaker_0");
		this.entityData.define(DATA_isEvolving, false);
		this.entityData.define(DATA_EvolveTime, 0);
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
		this.goalSelector.addGoal(1, new PanicGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = Endspeaker0Entity.this.getX();
				double y = Endspeaker0Entity.this.getY();
				double z = Endspeaker0Entity.this.getZ();
				Entity entity = Endspeaker0Entity.this;
				Level world = Endspeaker0Entity.this.level();
				return super.canUse() && IsEndspeakerStartingProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = Endspeaker0Entity.this.getX();
				double y = Endspeaker0Entity.this.getY();
				double z = Endspeaker0Entity.this.getZ();
				Entity entity = Endspeaker0Entity.this;
				Level world = Endspeaker0Entity.this.level();
				return super.canContinueToUse() && IsEndspeakerStartingProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = Endspeaker0Entity.this.getX();
				double y = Endspeaker0Entity.this.getY();
				double z = Endspeaker0Entity.this.getZ();
				Entity entity = Endspeaker0Entity.this;
				Level world = Endspeaker0Entity.this.level();
				return super.canUse() && IsEndspeakerStartingProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = Endspeaker0Entity.this.getX();
				double y = Endspeaker0Entity.this.getY();
				double z = Endspeaker0Entity.this.getZ();
				Entity entity = Endspeaker0Entity.this;
				Level world = Endspeaker0Entity.this.level();
				return super.canContinueToUse() && IsEndspeakerStartingProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(3, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = Endspeaker0Entity.this.getX();
				double y = Endspeaker0Entity.this.getY();
				double z = Endspeaker0Entity.this.getZ();
				Entity entity = Endspeaker0Entity.this;
				Level world = Endspeaker0Entity.this.level();
				return super.canUse() && IsEndspeakerStartingProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = Endspeaker0Entity.this.getX();
				double y = Endspeaker0Entity.this.getY();
				double z = Endspeaker0Entity.this.getZ();
				Entity entity = Endspeaker0Entity.this;
				Level world = Endspeaker0Entity.this.level();
				return super.canContinueToUse() && IsEndspeakerStartingProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(4, new FloatGoal(this));
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
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:seaborn_generic_hit"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:seaborn_generic_hit"));
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.FALL))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		return super.hurt(source, amount);
	}

	@Override
	public void setHealth(float pHealth){
		if (pHealth <= 0){
			super.setHealth(1);
			if (!isEvolving()) EndspeakerReviveProcedure.execute(this);
		}
		else super.setHealth(pHealth);
	}

	@Override
	public void die(DamageSource source) {
		if(!isEvolving()) EndspeakerReviveProcedure.execute(this);
	}

	private boolean isEvolving(){
		return this.entityData.get(DATA_EvolveTime) > 0;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		EndspeakerStartProcedure.execute(this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putBoolean("DataisEvolving", this.entityData.get(DATA_isEvolving));
		compound.putInt("DataEvolveTime", this.entityData.get(DATA_EvolveTime));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataisEvolving"))
			this.entityData.set(DATA_isEvolving, compound.getBoolean("DataisEvolving"));
		if (compound.contains("DataEvolveTime"))
			this.entityData.set(DATA_EvolveTime, compound.getInt("DataEvolveTime"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		Endspeaker0SkillsProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
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

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.15);
		builder = builder.add(Attributes.MAX_HEALTH, 16);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 1);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))

			) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.endspeaker_0.move"));
			}
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.endspeaker_0.die"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.endspeaker_0.idle"));
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
		if (this.deathTime == 35) {
			this.remove(Endspeaker0Entity.RemovalReason.KILLED);
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
		data.add(new AnimationController<>(this, "procedure", 0, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
