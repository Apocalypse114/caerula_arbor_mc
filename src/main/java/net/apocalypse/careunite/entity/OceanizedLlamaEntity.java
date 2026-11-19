
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
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.DifficultyInstance;
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

import net.apocalypse.careunite.procedures.SetLLamaChargedProcedure;
import net.apocalypse.careunite.procedures.LlamaTickProcedure;
import net.apocalypse.careunite.procedures.LlamaSyncHealthProcedure;
import net.apocalypse.careunite.procedures.LlamaSpitProcedure;
import net.apocalypse.careunite.procedures.LlamaSKillingProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import javax.annotation.Nullable;

import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.Difficulty;

public class OceanizedLlamaEntity extends Monster implements RangedAttackMob, GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(OceanizedLlamaEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(OceanizedLlamaEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(OceanizedLlamaEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Boolean> DATA_CHARGED = SynchedEntityData.defineId(OceanizedLlamaEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_POUR_P = SynchedEntityData.defineId(OceanizedLlamaEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";
	public int bonus = 0;
	public boolean fromTrader = false;

	public OceanizedLlamaEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.OCEANIZED_LLAMA.get(), world);
	}

	public OceanizedLlamaEntity(EntityType<OceanizedLlamaEntity> type, Level world) {
		super(type, world);
		xpReward = 7;
		setNoAi(false);
		setMaxUpStep(1f);
		setPersistenceRequired();
	}

	protected boolean shouldDespawnInPeaceful() {
      return !fromTrader;
   	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "oceanized_llama");
		this.entityData.define(DATA_CHARGED, false);
		this.entityData.define(DATA_POUR_P, 0);
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
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, WanderingTrader.class, true, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, IronGolem.class, true, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Villager.class, true, false));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, SnowGolem.class, true, false));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Illusioner.class, true, false));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, Pillager.class, true, false));
		this.targetSelector.addGoal(7, new NearestAttackableTargetGoal(this, Vindicator.class, true, false));
		this.targetSelector.addGoal(7, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(8, new FollowMobGoal(this, 1, 16f, 12f));
		this.goalSelector.addGoal(9, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = OceanizedLlamaEntity.this.getX();
				double y = OceanizedLlamaEntity.this.getY();
				double z = OceanizedLlamaEntity.this.getZ();
				Entity entity = OceanizedLlamaEntity.this;
				Level world = OceanizedLlamaEntity.this.level();
				return super.canUse() && LlamaSKillingProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedLlamaEntity.this.getX();
				double y = OceanizedLlamaEntity.this.getY();
				double z = OceanizedLlamaEntity.this.getZ();
				Entity entity = OceanizedLlamaEntity.this;
				Level world = OceanizedLlamaEntity.this.level();
				return super.canContinueToUse() && LlamaSKillingProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(10, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = OceanizedLlamaEntity.this.getX();
				double y = OceanizedLlamaEntity.this.getY();
				double z = OceanizedLlamaEntity.this.getZ();
				Entity entity = OceanizedLlamaEntity.this;
				Level world = OceanizedLlamaEntity.this.level();
				return super.canUse() && LlamaSKillingProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedLlamaEntity.this.getX();
				double y = OceanizedLlamaEntity.this.getY();
				double z = OceanizedLlamaEntity.this.getZ();
				Entity entity = OceanizedLlamaEntity.this;
				Level world = OceanizedLlamaEntity.this.level();
				return super.canContinueToUse() && LlamaSKillingProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(11, new FloatGoal(this));
		this.goalSelector.addGoal(1, new OceanizedLlamaEntity.RangedAttackGoal(this, 1.25, 30, 16f) {
			@Override
			public boolean canContinueToUse() {
				return this.canUse();
			}
		});
	}

	public class RangedAttackGoal extends Goal {
		private final Mob mob;
		private final RangedAttackMob rangedAttackMob;
		@Nullable
		private LivingEntity target;
		private int attackTime = -1;
		private final double speedModifier;
		private int seeTime;
		private final int attackIntervalMin;
		private final int attackIntervalMax;
		private final float attackRadius;
		private final float attackRadiusSqr;

		public RangedAttackGoal(RangedAttackMob p_25768_, double p_25769_, int p_25770_, float p_25771_) {
			this(p_25768_, p_25769_, p_25770_, p_25770_, p_25771_);
		}

		public RangedAttackGoal(RangedAttackMob p_25773_, double p_25774_, int p_25775_, int p_25776_, float p_25777_) {
			if (!(p_25773_ instanceof LivingEntity)) {
				throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
			} else {
				this.rangedAttackMob = p_25773_;
				this.mob = (Mob) p_25773_;
				this.speedModifier = p_25774_;
				this.attackIntervalMin = p_25775_;
				this.attackIntervalMax = p_25776_;
				this.attackRadius = p_25777_;
				this.attackRadiusSqr = p_25777_ * p_25777_;
				this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
			}
		}

		public boolean canUse() {
			if (this.mob.level().getDifficulty() == Difficulty.PEACEFUL) return false;
			LivingEntity livingentity = this.mob.getTarget();
			if (livingentity != null && livingentity.isAlive()) {
				this.target = livingentity;
				return true;
			} else {
				return false;
			}
		}

		public boolean canContinueToUse() {
			return this.canUse() || this.target.isAlive() && !this.mob.getNavigation().isDone();
		}

		public void stop() {
			this.target = null;
			this.seeTime = 0;
			this.attackTime = -1;
			((OceanizedLlamaEntity) rangedAttackMob).entityData.set(SHOOT, false);
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		public void tick() {
			double d0 = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
			boolean flag = this.mob.getSensing().hasLineOfSight(this.target);
			if (flag) {
				++this.seeTime;
			} else {
				this.seeTime = 0;
			}
			if (!(d0 > (double) this.attackRadiusSqr) && this.seeTime >= 5) {
				this.mob.getNavigation().stop();
			} else {
				this.mob.getNavigation().moveTo(this.target, this.speedModifier);
			}
			this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
			if (--this.attackTime == 0) {
				if (!flag) {
					((OceanizedLlamaEntity) rangedAttackMob).entityData.set(SHOOT, false);
					return;
				}
				((OceanizedLlamaEntity) rangedAttackMob).entityData.set(SHOOT, true);
				float f = (float) Math.sqrt(d0) / this.attackRadius;
				float f1 = Mth.clamp(f, 0.1F, 1.0F);
				this.rangedAttackMob.performRangedAttack(this.target, f1);
				this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
			} else if (this.attackTime < 0) {
				this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) this.attackRadius, (double) this.attackIntervalMin, (double) this.attackIntervalMax));
			} else
				((OceanizedLlamaEntity) rangedAttackMob).entityData.set(SHOOT, false);
		}
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
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.llama.ambient"));
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.llama.step")), 0.15f, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.llama.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.llama.death"));
	}

	@Override
	public void thunderHit(ServerLevel serverWorld, LightningBolt lightningBolt) {
		super.thunderHit(serverWorld, lightningBolt);
		SetLLamaChargedProcedure.execute(this);
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.DROWN))
			return false;
		return super.hurt(source, amount);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		LlamaSyncHealthProcedure.execute(this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putBoolean("DataCHARGED", this.entityData.get(DATA_CHARGED));
		compound.putInt("DataPOUR_P", this.entityData.get(DATA_POUR_P));
		compound.putInt("DataBonus", bonus);
		compound.putBoolean("fromTrader", fromTrader);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataCHARGED"))
			this.entityData.set(DATA_CHARGED, compound.getBoolean("DataCHARGED"));
		if (compound.contains("DataPOUR_P"))
			this.entityData.set(DATA_POUR_P, compound.getInt("DataPOUR_P"));
		if (compound.contains("DataBonus"))
			this.bonus = compound.getInt("DataBonus");
		if (compound.contains("fromTrader"))
			this.fromTrader = compound.getBoolean("fromTrader");
	}

	@Override
	public void baseTick() {
		super.baseTick();
		LlamaTickProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public void performRangedAttack(LivingEntity target, float flval) {
		this.lookAt(target, 15, 15);
		int rep = Mth.nextInt(this.random, 1, 3) + bonus;
		for (int i = 0; i< rep; i++) LlamaSpitProcedure.execute(this);
		if (this.entityData.get(DATA_POUR_P) <= 0){
			if ((this.isCharged() && this.random.nextDouble() < 0.25 + bonus * 0.05)
				||(!this.isCharged() && this.random.nextDouble() < 0.15 + bonus * 0.05)){
				this.setAnimation("animation.oceanized_llama.pour");
				this.entityData.set(DATA_POUR_P, 65);
				if (bonus < 3) bonus++;
			}
		}
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 24);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.25);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))

			) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_llama.move"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_llama.idle"));
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
			this.remove(OceanizedLlamaEntity.RemovalReason.KILLED);
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

	public boolean isCharged(){
		return this.entityData.get(DATA_CHARGED);
	}
}
