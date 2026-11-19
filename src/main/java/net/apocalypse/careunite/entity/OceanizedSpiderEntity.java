
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

import net.minecraft.world.phys.Vec3;
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
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
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
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.procedures.SpiderMuteHandlerProcedure;
import net.apocalypse.careunite.procedures.OceanizedPlayerProcedure;
import net.apocalypse.careunite.procedures.LayOvaryProcedure;
import net.apocalypse.careunite.procedures.CanAttackAnimalsProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import java.util.EnumSet;

public class OceanizedSpiderEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(OceanizedSpiderEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(OceanizedSpiderEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(OceanizedSpiderEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_mute_time = SynchedEntityData.defineId(OceanizedSpiderEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public OceanizedSpiderEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.OCEANIZED_SPIDER.get(), world);
	}

	public OceanizedSpiderEntity(EntityType<OceanizedSpiderEntity> type, Level world) {
		super(type, world);
		xpReward = 5;
		setNoAi(false);
		setMaxUpStep(0.6f);
		this.moveControl = new FlyingMoveControl(this, 10, true);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "oceanized_spider");
		this.entityData.define(DATA_mute_time, 0);
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
	protected PathNavigation createNavigation(Level world) {
		return new FlyingPathNavigation(this, world);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.75, true) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 3.24;
			}
		});
		this.goalSelector.addGoal(3, new Goal() {
			{
				this.setFlags(EnumSet.of(Goal.Flag.MOVE));
			}

			public boolean canUse() {
				if (OceanizedSpiderEntity.this.getTarget() != null && !OceanizedSpiderEntity.this.getMoveControl().hasWanted()) {
					return true;
				} else {
					return false;
				}
			}

			@Override
			public boolean canContinueToUse() {
				return OceanizedSpiderEntity.this.getMoveControl().hasWanted() && OceanizedSpiderEntity.this.getTarget() != null && OceanizedSpiderEntity.this.getTarget().isAlive();
			}

			@Override
			public void start() {
				LivingEntity livingentity = OceanizedSpiderEntity.this.getTarget();
				Vec3 vec3d = livingentity.getEyePosition(1);
				OceanizedSpiderEntity.this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, 1.5);
			}

			@Override
			public void tick() {
				LivingEntity livingentity = OceanizedSpiderEntity.this.getTarget();
				if (OceanizedSpiderEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
					OceanizedSpiderEntity.this.doHurtTarget(livingentity);
				} else {
					double d0 = OceanizedSpiderEntity.this.distanceToSqr(livingentity);
					if (d0 < 16) {
						Vec3 vec3d = livingentity.getEyePosition(1);
						OceanizedSpiderEntity.this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, 1.5);
					}
				}
			}
		});
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, IronGolem.class, true, false));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, SnowGolem.class, true, false));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, Villager.class, true, false));
		this.targetSelector.addGoal(7, new NearestAttackableTargetGoal(this, Illusioner.class, true, false));
		this.targetSelector.addGoal(8, new NearestAttackableTargetGoal(this, Pillager.class, true, false));
		this.targetSelector.addGoal(9, new NearestAttackableTargetGoal(this, Vindicator.class, true, false));
		this.targetSelector.addGoal(10, new NearestAttackableTargetGoal(this, Witch.class, true, false));
		this.targetSelector.addGoal(11, new NearestAttackableTargetGoal(this, Piglin.class, true, false));
		this.targetSelector.addGoal(12, new NearestAttackableTargetGoal(this, PiglinBrute.class, true, false));
		this.targetSelector.addGoal(13, new NearestAttackableTargetGoal(this, ZombifiedPiglin.class, true, false));
		this.targetSelector.addGoal(14, new RunFishEntity.AttackPlayerGoal(this));
		this.goalSelector.addGoal(16, new RandomStrollGoal(this, 1.5, 20) {
			@Override
			protected Vec3 getPosition() {
				RandomSource random = OceanizedSpiderEntity.this.getRandom();
				double dir_x = OceanizedSpiderEntity.this.getX() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_y = OceanizedSpiderEntity.this.getY() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_z = OceanizedSpiderEntity.this.getZ() + ((random.nextFloat() * 2 - 1) * 16);
				return new Vec3(dir_x, dir_y, dir_z);
			}
		});
		this.goalSelector.addGoal(17, new RandomLookAroundGoal(this));
	}

	@Override
	public MobType getMobType() {
		return MobType.ARTHROPOD;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.spider.ambient"));
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.spider.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.spider.death"));
	}

	@Override
	public boolean causeFallDamage(float l, float d, DamageSource source) {
		return false;
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
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("Datamute_time", this.entityData.get(DATA_mute_time));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("Datamute_time"))
			this.entityData.set(DATA_mute_time, compound.getInt("Datamute_time"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		SpiderMuteHandlerProcedure.execute(this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	@Override
	public void setNoGravity(boolean ignored) {
		super.setNoGravity(true);
	}

	public void aiStep() {
		super.aiStep();
		this.setNoGravity(true);
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
		builder = builder.add(Attributes.MAX_HEALTH, 32);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 7);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.85);
		builder = builder.add(Attributes.FLYING_SPEED, 0.25);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) && this.onGround()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_spider.walk"));
			}
			if (!this.onGround()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_spider.fly"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_spider.idle"));
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
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.oceanized_spider.attack"));
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
			this.remove(OceanizedSpiderEntity.RemovalReason.KILLED);
			this.dropExperience();
			LayOvaryProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
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
		data.add(new AnimationController<>(this, "attacking", 2, this::attackingPredicate));
		data.add(new AnimationController<>(this, "procedure", 2, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
