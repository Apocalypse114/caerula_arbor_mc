
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

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.procedures.TimedSpawnExcrescenceProcedure;
import net.apocalypse.careunite.procedures.RockSpiderDieProcedure;
import net.apocalypse.careunite.procedures.OceanizedPlayerProcedure;
import net.apocalypse.careunite.procedures.IsRockSpiderDurativeProcedure;
import net.apocalypse.careunite.procedures.CanAttackAnimalsProcedure;
import net.apocalypse.careunite.procedures.CallExcresProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.core.registries.Registries;
import net.apocalypse.careunite.CaerulaArborMod;
import net.apocalypse.careunite.Seaborn;

public class TideutantRockSpiderEntity extends Monster implements GeoEntity, Seaborn {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(TideutantRockSpiderEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(TideutantRockSpiderEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(TideutantRockSpiderEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_duration = SynchedEntityData.defineId(TideutantRockSpiderEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public TideutantRockSpiderEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.TIDUTANT_ROCK_SPIDER.get(), world);
	}

	public Seaborn.Type getSeabornType() {
    	return Seaborn.Type.CONVERTED;
    }

    public Seaborn.Tier getTier(){
        return Seaborn.Tier.ELITE;
    }

	public TideutantRockSpiderEntity(EntityType<TideutantRockSpiderEntity> type, Level world) {
		super(type, world);
		xpReward = 16;
		setNoAi(false);
		setMaxUpStep(0.6f);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "tideutant_rock_spider");
		this.entityData.define(DATA_duration, 0);
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
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1, true) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 12.25;
			}

			@Override
			public boolean canUse() {
				double x = TideutantRockSpiderEntity.this.getX();
				double y = TideutantRockSpiderEntity.this.getY();
				double z = TideutantRockSpiderEntity.this.getZ();
				Entity entity = TideutantRockSpiderEntity.this;
				Level world = TideutantRockSpiderEntity.this.level();
				return super.canUse() && IsRockSpiderDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = TideutantRockSpiderEntity.this.getX();
				double y = TideutantRockSpiderEntity.this.getY();
				double z = TideutantRockSpiderEntity.this.getZ();
				Entity entity = TideutantRockSpiderEntity.this;
				Level world = TideutantRockSpiderEntity.this.level();
				return super.canContinueToUse() && IsRockSpiderDurativeProcedure.execute(entity);
			}

		});
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, SnowGolem.class, true, false));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Villager.class, true, false));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, Illusioner.class, true, false));
		this.targetSelector.addGoal(7, new NearestAttackableTargetGoal(this, Pillager.class, true, false));
		this.targetSelector.addGoal(8, new NearestAttackableTargetGoal(this, Vindicator.class, true, false));
		this.targetSelector.addGoal(9, new NearestAttackableTargetGoal(this, Witch.class, true, false));
		this.targetSelector.addGoal(10, new NearestAttackableTargetGoal(this, Piglin.class, true, false));
		this.targetSelector.addGoal(11, new NearestAttackableTargetGoal(this, PiglinBrute.class, true, false));
		this.targetSelector.addGoal(12, new NearestAttackableTargetGoal(this, ZombifiedPiglin.class, true, false));
		this.targetSelector.addGoal(13, new RunFishEntity.AttackPlayerGoal(this));
		this.goalSelector.addGoal(15, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = TideutantRockSpiderEntity.this.getX();
				double y = TideutantRockSpiderEntity.this.getY();
				double z = TideutantRockSpiderEntity.this.getZ();
				Entity entity = TideutantRockSpiderEntity.this;
				Level world = TideutantRockSpiderEntity.this.level();
				return super.canUse() && IsRockSpiderDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = TideutantRockSpiderEntity.this.getX();
				double y = TideutantRockSpiderEntity.this.getY();
				double z = TideutantRockSpiderEntity.this.getZ();
				Entity entity = TideutantRockSpiderEntity.this;
				Level world = TideutantRockSpiderEntity.this.level();
				return super.canContinueToUse() && IsRockSpiderDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(16, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(17, new FloatGoal(this));
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
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.spider.ambient")), 0.15f, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:seaborn_generic_hit"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:seaborn_death"));
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		CallExcresProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), source.getEntity());
		if (source.is(DamageTypes.FALL))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		return super.hurt(source, amount);
	}

	public int lastAttackTick = 0;
	@Override
	public boolean doHurtTarget(Entity entity){
		if (this.tickCount > lastAttackTick && this.tickCount < lastAttackTick + 16) return false;
		lastAttackTick = this.tickCount;
		CaerulaArborMod.queueServerWork(9, () -> {
			if (this.isAlive() && this.distanceTo(entity) <= 4) {
				entity.hurt(
					new DamageSource(
						this.level().registryAccess()
							.registryOrThrow(Registries.DAMAGE_TYPE)
							.getHolderOrThrow(atk), this),
					(float) this.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
			}
		});
		return true;
	}

	public static final ResourceKey<DamageType> atk = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"));

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("Dataduration", this.entityData.get(DATA_duration));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("Dataduration"))
			this.entityData.set(DATA_duration, compound.getInt("Dataduration"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		TimedSpawnExcrescenceProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void doPush(Entity entityIn) {
	}

	@Override
	protected void pushEntities() {
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.18);
		builder = builder.add(Attributes.MAX_HEALTH, 85);
		builder = builder.add(Attributes.ARMOR, 3);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 7);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.75);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.15);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))

			) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.tidutant_rock_spider.move"));
			}
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.tidutant_rock_spider.die"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.tidutant_rock_spider.idle"));
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
		if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.tidutant_rock_spider.attack"));
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
			this.remove(TideutantRockSpiderEntity.RemovalReason.KILLED);
			this.dropExperience();
			RockSpiderDieProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ());
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
