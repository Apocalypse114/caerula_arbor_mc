
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
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.monster.Evoker;
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

import net.apocalypse.careunite.procedures.SilverfishDiveIntoStoneProcedure;
import net.apocalypse.careunite.procedures.OceanizedPlayerProcedure;
import net.apocalypse.careunite.procedures.CallSilverfishAroundProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class OceanizedSilverfishEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(OceanizedSilverfishEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(OceanizedSilverfishEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(OceanizedSilverfishEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_DIVE_TICK = SynchedEntityData.defineId(OceanizedSilverfishEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_DIVING = SynchedEntityData.defineId(OceanizedSilverfishEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public OceanizedSilverfishEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.OCEANIZED_SILVERFISH.get(), world);
	}

	public OceanizedSilverfishEntity(EntityType<OceanizedSilverfishEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
		setMaxUpStep(0.7f);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "oceanized_silverfish");
		this.entityData.define(DATA_DIVE_TICK, 300);
		this.entityData.define(DATA_DIVING, -1);
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
				return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
			}
		});
		this.targetSelector.addGoal(3, new RunFishEntity.AttackPlayerGoal(this));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, IronGolem.class, true, false));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Villager.class, true, false));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, Illusioner.class, true, false));
		this.targetSelector.addGoal(7, new NearestAttackableTargetGoal(this, Pillager.class, true, false));
		this.targetSelector.addGoal(8, new NearestAttackableTargetGoal(this, Evoker.class, true, false));
		this.targetSelector.addGoal(9, new NearestAttackableTargetGoal(this, Vindicator.class, true, false));
		this.goalSelector.addGoal(10, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(12, new FloatGoal(this));
	}

	@Override
	public MobType getMobType() {
		return MobType.WATER;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.silverfish.ambient"));
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.silverfish.step")), 0.15f, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.silverfish.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.silverfish.death"));
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		CallSilverfishAroundProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ());
		if (source.is(DamageTypes.DROWN))
			return false;
		return super.hurt(source, amount);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataDIVE_TICK", this.entityData.get(DATA_DIVE_TICK));
		compound.putInt("DataDIVING", this.entityData.get(DATA_DIVING));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataDIVE_TICK"))
			this.entityData.set(DATA_DIVE_TICK, compound.getInt("DataDIVE_TICK"));
		if (compound.contains("DataDIVING"))
			this.entityData.set(DATA_DIVING, compound.getInt("DataDIVING"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		SilverfishDiveIntoStoneProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
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
		builder = builder.add(Attributes.MAX_HEALTH, 12);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 2);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_silverfish.idle"));
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
			this.remove(OceanizedSilverfishEntity.RemovalReason.KILLED);
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
		data.add(new AnimationController<>(this, "movement", 2, this::movementPredicate));
		data.add(new AnimationController<>(this, "procedure", 2, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
