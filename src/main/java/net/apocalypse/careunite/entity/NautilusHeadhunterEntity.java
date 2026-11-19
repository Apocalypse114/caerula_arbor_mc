
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
import net.minecraftforge.common.ForgeMod;

import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacements;
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
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.procedures.NautilusSKillsProcedure;
import net.apocalypse.careunite.procedures.NautilusKillBonusProcedure;
import net.apocalypse.careunite.procedures.MarineSeabornSpawnProcedure;
import net.apocalypse.careunite.procedures.GetOffProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import java.util.List;

public class NautilusHeadhunterEntity extends Animal implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(NautilusHeadhunterEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(NautilusHeadhunterEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(NautilusHeadhunterEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_DRY_TICK = SynchedEntityData.defineId(NautilusHeadhunterEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_BONUS = SynchedEntityData.defineId(NautilusHeadhunterEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public NautilusHeadhunterEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.NAUTILUS_HEADHUNTER.get(), world);
	}

	public NautilusHeadhunterEntity(EntityType<NautilusHeadhunterEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
		setMaxUpStep(0.6f);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0);
		this.moveControl = new MoveControl(this) {
			@Override
			public void tick() {
				if (NautilusHeadhunterEntity.this.isInWater())
					NautilusHeadhunterEntity.this.setDeltaMovement(NautilusHeadhunterEntity.this.getDeltaMovement().add(0, 0.005, 0));
				if (this.operation == MoveControl.Operation.MOVE_TO && !NautilusHeadhunterEntity.this.getNavigation().isDone()) {
					double dx = this.wantedX - NautilusHeadhunterEntity.this.getX();
					double dy = this.wantedY - NautilusHeadhunterEntity.this.getY();
					double dz = this.wantedZ - NautilusHeadhunterEntity.this.getZ();
					float f = (float) (Mth.atan2(dz, dx) * (double) (180 / Math.PI)) - 90;
					float f1 = (float) (this.speedModifier * NautilusHeadhunterEntity.this.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
					NautilusHeadhunterEntity.this.setYRot(this.rotlerp(NautilusHeadhunterEntity.this.getYRot(), f, 10));
					NautilusHeadhunterEntity.this.yBodyRot = NautilusHeadhunterEntity.this.getYRot();
					NautilusHeadhunterEntity.this.yHeadRot = NautilusHeadhunterEntity.this.getYRot();
					if (NautilusHeadhunterEntity.this.isInWater()) {
						NautilusHeadhunterEntity.this.setSpeed((float) NautilusHeadhunterEntity.this.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
						float f2 = -(float) (Mth.atan2(dy, (float) Math.sqrt(dx * dx + dz * dz)) * (180 / Math.PI));
						f2 = Mth.clamp(Mth.wrapDegrees(f2), -85, 85);
						NautilusHeadhunterEntity.this.setXRot(this.rotlerp(NautilusHeadhunterEntity.this.getXRot(), f2, 5));
						float f3 = Mth.cos(NautilusHeadhunterEntity.this.getXRot() * (float) (Math.PI / 180.0));
						NautilusHeadhunterEntity.this.setZza(f3 * f1);
						NautilusHeadhunterEntity.this.setYya((float) (f1 * dy));
					} else {
						NautilusHeadhunterEntity.this.setSpeed(f1 * 0.05F);
					}
				} else {
					NautilusHeadhunterEntity.this.setSpeed(0);
					NautilusHeadhunterEntity.this.setYya(0);
					NautilusHeadhunterEntity.this.setZza(0);
				}
			}
		};
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "nautilus_headhunter");
		this.entityData.define(DATA_DRY_TICK, 0);
		this.entityData.define(DATA_BONUS, 0);
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public String getTexture() {
		return this.entityData.get(TEXTURE);
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 0.35F;
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		return new WaterBoundPathNavigation(this, world);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
			}
		});
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Drowned.class, true, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, SnowGolem.class, true, false));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Villager.class, true, false));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, Illusioner.class, true, false));
		this.targetSelector.addGoal(7, new NearestAttackableTargetGoal(this, Pillager.class, true, false));
		this.targetSelector.addGoal(8, new NearestAttackableTargetGoal(this, Vindicator.class, true, false));
		this.targetSelector.addGoal(9, new NearestAttackableTargetGoal(this, Witch.class, true, false));
		this.targetSelector.addGoal(10, new NearestAttackableTargetGoal(this, Evoker.class, true, false));
		this.goalSelector.addGoal(11, new RandomSwimmingGoal(this, 1, 40));
		this.goalSelector.addGoal(12, new RandomLookAroundGoal(this));
	}

	@Override
	public MobType getMobType() {
		return MobType.WATER;
	}

	protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
		super.dropCustomDeathLoot(source, looting, recentlyHitIn);
		this.spawnAtLocation(new ItemStack(Items.NAUTILUS_SHELL));
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.turtle.ambient_land"));
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.turtle.swim")), 0.15f, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.turtle.hurt_baby"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.turtle.death_baby"));
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		GetOffProcedure.execute(this);
		if (source.is(DamageTypes.DROWN))
			return false;
		return super.hurt(source, amount);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataDRY_TICK", this.entityData.get(DATA_DRY_TICK));
		compound.putInt("DataBONUS", this.entityData.get(DATA_BONUS));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataDRY_TICK"))
			this.entityData.set(DATA_DRY_TICK, compound.getInt("DataDRY_TICK"));
		if (compound.contains("DataBONUS"))
			this.entityData.set(DATA_BONUS, compound.getInt("DataBONUS"));
	}

	@Override
	public void awardKillScore(Entity entity, int score, DamageSource damageSource) {
		super.awardKillScore(entity, score, damageSource);
		NautilusKillBonusProcedure.execute(this);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		NautilusSKillsProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
		NautilusHeadhunterEntity retval = CaerulaArborModEntities.NAUTILUS_HEADHUNTER.get().create(serverWorld);
		retval.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(retval.blockPosition()), MobSpawnType.BREEDING, null, null);
		return retval;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return List.of().contains(stack.getItem());
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader world) {
		return world.isUnobstructed(this);
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.updateSwingTime();
	}

	public static void init() {
		SpawnPlacements.register(CaerulaArborModEntities.NAUTILUS_HEADHUNTER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			return MarineSeabornSpawnProcedure.execute(world, x, y, z);
		});
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 2);
		builder = builder.add(Attributes.MAX_HEALTH, 25);
		builder = builder.add(Attributes.ARMOR, 6);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.5);
		builder = builder.add(ForgeMod.SWIM_SPEED.get(), 2);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))

					&& !this.isAggressive()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.nultilus_headhunter.swim"));
			}
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.nultilus_headhunter.die"));
			}
			if (this.isAggressive() && event.isMoving()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.nultilus_headhunter.sprint"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.nultilus_headhunter.idle"));
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
			this.remove(NautilusHeadhunterEntity.RemovalReason.KILLED);
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
