
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
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
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
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.procedures.SkadiCorruptedSkillsProcedure;
import net.apocalypse.careunite.procedures.IsCorruptedDurativeProcedure;
import net.apocalypse.careunite.procedures.CurruptedConvertProcedure;
import net.apocalypse.careunite.procedures.CorruptedDoHurtTargetProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModSounds;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.tags.DamageTypeTags;

import javax.annotation.Nullable;

public class SkadiCorruptedEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(SkadiCorruptedEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(SkadiCorruptedEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(SkadiCorruptedEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_convertP = SynchedEntityData.defineId(SkadiCorruptedEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_mayCorrupt = SynchedEntityData.defineId(SkadiCorruptedEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_duration = SynchedEntityData.defineId(SkadiCorruptedEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_convertTick = SynchedEntityData.defineId(SkadiCorruptedEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_deal = SynchedEntityData.defineId(SkadiCorruptedEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_phase = SynchedEntityData.defineId(SkadiCorruptedEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.RED, ServerBossEvent.BossBarOverlay.NOTCHED_6);

	public SkadiCorruptedEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.SKADI_CORRUPTED.get(), world);
	}

	public SkadiCorruptedEntity(EntityType<SkadiCorruptedEntity> type, Level world) {
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
		this.entityData.define(TEXTURE, "skadi_corrupted_0");
		this.entityData.define(DATA_convertP, 900);
		this.entityData.define(DATA_mayCorrupt, true);
		this.entityData.define(DATA_duration, 0);
		this.entityData.define(DATA_convertTick, 1000);
		this.entityData.define(DATA_deal, 0);
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
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 4;
			}

			@Override
			public boolean canUse() {
				double x = SkadiCorruptedEntity.this.getX();
				double y = SkadiCorruptedEntity.this.getY();
				double z = SkadiCorruptedEntity.this.getZ();
				Entity entity = SkadiCorruptedEntity.this;
				Level world = SkadiCorruptedEntity.this.level();
				return super.canUse() && IsCorruptedDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = SkadiCorruptedEntity.this.getX();
				double y = SkadiCorruptedEntity.this.getY();
				double z = SkadiCorruptedEntity.this.getZ();
				Entity entity = SkadiCorruptedEntity.this;
				Level world = SkadiCorruptedEntity.this.level();
				return super.canContinueToUse() && IsCorruptedDurativeProcedure.execute(entity);
			}

		});
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, SnowGolem.class, true, false));
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = SkadiCorruptedEntity.this.getX();
				double y = SkadiCorruptedEntity.this.getY();
				double z = SkadiCorruptedEntity.this.getZ();
				Entity entity = SkadiCorruptedEntity.this;
				Level world = SkadiCorruptedEntity.this.level();
				return super.canUse() && IsCorruptedDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = SkadiCorruptedEntity.this.getX();
				double y = SkadiCorruptedEntity.this.getY();
				double z = SkadiCorruptedEntity.this.getZ();
				Entity entity = SkadiCorruptedEntity.this;
				Level world = SkadiCorruptedEntity.this.level();
				return super.canContinueToUse() && IsCorruptedDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(6, new OpenDoorGoal(this, true));
		this.goalSelector.addGoal(7, new OpenDoorGoal(this, false));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = SkadiCorruptedEntity.this.getX();
				double y = SkadiCorruptedEntity.this.getY();
				double z = SkadiCorruptedEntity.this.getZ();
				Entity entity = SkadiCorruptedEntity.this;
				Level world = SkadiCorruptedEntity.this.level();
				return super.canUse() && IsCorruptedDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = SkadiCorruptedEntity.this.getX();
				double y = SkadiCorruptedEntity.this.getY();
				double z = SkadiCorruptedEntity.this.getZ();
				Entity entity = SkadiCorruptedEntity.this;
				Level world = SkadiCorruptedEntity.this.level();
				return super.canContinueToUse() && IsCorruptedDurativeProcedure.execute(entity);
			}
		});
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
		return CaerulaArborModSounds.SEABORN_MASSIVE_HURT.get();
	}

	@Override
	public SoundEvent getDeathSound() {
		return CaerulaArborModSounds.SEABORN_MASSIVE_DEATH.get();
	}
	
	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.IN_FIRE))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		float newAmount = amount;
		if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)){
			int p = getPhase();
			if(p<0.5) newAmount = amount * 0.15f;
			else if(p<1.5) newAmount = amount * 0.5f;
		}
		return super.hurt(source, newAmount);
	}

	@Override
	public boolean doHurtTarget(Entity pEntity){
		CorruptedDoHurtTargetProcedure.execute(this.level(), pEntity, this);
		return true;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataconvertP", this.entityData.get(DATA_convertP));
		compound.putBoolean("DatamayCorrupt", this.entityData.get(DATA_mayCorrupt));
		compound.putInt("Dataduration", this.entityData.get(DATA_duration));
		compound.putInt("DataconvertTick", this.entityData.get(DATA_convertTick));
		compound.putInt("Datadeal", this.entityData.get(DATA_deal));
		compound.putInt("Dataphase", this.entityData.get(DATA_phase));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataconvertP"))
			this.entityData.set(DATA_convertP, compound.getInt("DataconvertP"));
		if (compound.contains("DatamayCorrupt"))
			this.entityData.set(DATA_mayCorrupt, compound.getBoolean("DatamayCorrupt"));
		if (compound.contains("Dataduration"))
			this.entityData.set(DATA_duration, compound.getInt("Dataduration"));
		if (compound.contains("DataconvertTick"))
			this.entityData.set(DATA_convertTick, compound.getInt("DataconvertTick"));
		if (compound.contains("Datadeal"))
			this.entityData.set(DATA_deal, compound.getInt("Datadeal"));
		if (compound.contains("Dataphase"))
			this.entityData.set(DATA_phase, compound.getInt("Dataphase"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		SkadiCorruptedSkillsProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		if (entityData.get(DATA_mayCorrupt) || getPhase() >= 2) bossInfo.setColor(ServerBossEvent.BossBarColor.RED);
		else bossInfo.setColor(ServerBossEvent.BossBarColor.BLUE);
		this.refreshDimensions();
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand){
        if (player.getMainHandItem().getItem() == CaerulaArborModItems.CORRUPTED_HEART_SPAWNER.get()){
            entityData.set(DATA_convertP, 1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
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

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		double x = this.getX(), y = this.getY(), z = this.getZ();
		if(world instanceof Level level && !level.isClientSide()){
			level.playSound(
				null, BlockPos.containing(x, y, z), 
				ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:silence2")),
				SoundSource.NEUTRAL, 1, 1);
		}
		return retval;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.12);
		builder = builder.add(Attributes.MAX_HEALTH, 405);
		builder = builder.add(Attributes.ARMOR, 9);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 15);
		builder = builder.add(Attributes.FOLLOW_RANGE, 36);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 10);
		builder = builder.add(CaerulaArborModAttributes.SANITY_MODIFIER.get(), 0.02);
		builder = builder.add(CaerulaArborModAttributes.MAGIC_RESISTANCE.get(), 30);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.1F && event.getLimbSwingAmount() < 0.1F))

			) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.skadi_corrupted.move"));
			}
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.skadi_corrupted.convert_in_23"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.skadi_corrupted.idle"));
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
		if (this.swinging && this.lastSwing + 40L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.skadi_corrupted.attack"));
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
			CurruptedConvertProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
			this.remove(SkadiCorruptedEntity.RemovalReason.KILLED);
			this.dropExperience();
		}
	}

	@Override
	public void setHealth(float pHealth){
		if (getPhase() < 2 && pHealth <= 0 && entityData.get(DATA_mayCorrupt)){
			entityData.set(DATA_convertP, 1);
			super.setHealth(this.getMaxHealth());
			return;
		}
		super.setHealth(pHealth);
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

	public int getPhase(){
		return entityData.get(DATA_phase);
	}
}
