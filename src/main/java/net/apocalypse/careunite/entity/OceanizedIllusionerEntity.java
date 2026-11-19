
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
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.Witch;
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
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.util.Mth;
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

import net.apocalypse.careunite.procedures.SwapWithIllusionProcedure;
import net.apocalypse.careunite.procedures.OceanizedPlayerProcedure;
import net.apocalypse.careunite.procedures.IsIllusionerDurativeProcedure;
import net.apocalypse.careunite.procedures.InitMyRavagerProcedure;
import net.apocalypse.careunite.procedures.IllusionerSkillsProcedure;
import net.apocalypse.careunite.procedures.CanAttackAnimalsProcedure;
import net.apocalypse.careunite.procedures.GetIllusionNumProcedure;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.OceanIllusionEntity;
import net.minecraft.world.phys.AABB;

import java.util.List;
import javax.annotation.Nullable;
import java.util.EnumSet;

public class OceanizedIllusionerEntity extends Monster implements RangedAttackMob, GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(OceanizedIllusionerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(OceanizedIllusionerEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(OceanizedIllusionerEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_spellP = SynchedEntityData.defineId(OceanizedIllusionerEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_mirrorP = SynchedEntityData.defineId(OceanizedIllusionerEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_duration = SynchedEntityData.defineId(OceanizedIllusionerEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.GREEN, ServerBossEvent.BossBarOverlay.PROGRESS);

	public OceanizedIllusionerEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.OCEANIZED_ILLUSIONER.get(), world);
	}

	public OceanizedIllusionerEntity(EntityType<OceanizedIllusionerEntity> type, Level world) {
		super(type, world);
		xpReward = 64;
		setNoAi(false);
		setMaxUpStep(1f);
		setPersistenceRequired();
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(CaerulaArborModItems.CHITIN_BOW.get()));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "oceanized_illusioner");
		this.entityData.define(DATA_spellP, 180);
		this.entityData.define(DATA_mirrorP, 340);
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
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, IronGolem.class, true, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, SnowGolem.class, true, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Villager.class, true, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Illusioner.class, true, true));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, Pillager.class, true, true));
		this.targetSelector.addGoal(7, new NearestAttackableTargetGoal(this, Vindicator.class, true, true));
		this.targetSelector.addGoal(8, new NearestAttackableTargetGoal(this, Witch.class, true, true));
		this.targetSelector.addGoal(9, new NearestAttackableTargetGoal(this, Piglin.class, true, true));
		this.targetSelector.addGoal(10, new NearestAttackableTargetGoal(this, PiglinBrute.class, true, true));
		this.targetSelector.addGoal(11, new NearestAttackableTargetGoal(this, ZombifiedPiglin.class, true, true));
		this.targetSelector.addGoal(12, new RunFishEntity.AttackPlayerGoal(this));
		this.goalSelector.addGoal(14, new OpenDoorGoal(this, false));
		this.goalSelector.addGoal(15, new OpenDoorGoal(this, true));
		this.goalSelector.addGoal(16, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = OceanizedIllusionerEntity.this.getX();
				double y = OceanizedIllusionerEntity.this.getY();
				double z = OceanizedIllusionerEntity.this.getZ();
				Entity entity = OceanizedIllusionerEntity.this;
				Level world = OceanizedIllusionerEntity.this.level();
				return super.canUse() && IsIllusionerDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedIllusionerEntity.this.getX();
				double y = OceanizedIllusionerEntity.this.getY();
				double z = OceanizedIllusionerEntity.this.getZ();
				Entity entity = OceanizedIllusionerEntity.this;
				Level world = OceanizedIllusionerEntity.this.level();
				return super.canContinueToUse() && IsIllusionerDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(17, new FloatGoal(this));
		this.goalSelector.addGoal(18, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = OceanizedIllusionerEntity.this.getX();
				double y = OceanizedIllusionerEntity.this.getY();
				double z = OceanizedIllusionerEntity.this.getZ();
				Entity entity = OceanizedIllusionerEntity.this;
				Level world = OceanizedIllusionerEntity.this.level();
				return super.canUse() && IsIllusionerDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedIllusionerEntity.this.getX();
				double y = OceanizedIllusionerEntity.this.getY();
				double z = OceanizedIllusionerEntity.this.getZ();
				Entity entity = OceanizedIllusionerEntity.this;
				Level world = OceanizedIllusionerEntity.this.level();
				return super.canContinueToUse() && IsIllusionerDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(1, new OceanizedIllusionerEntity.RangedAttackGoal(this, 1.25, 30, 4f) {
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
		public boolean isDurative(Entity entity){
			return IsIllusionerDurativeProcedure.execute(entity);
		}

		public boolean canUse() {
			LivingEntity livingentity = this.mob.getTarget();
			if (livingentity != null && livingentity.isAlive()) {
				this.target = livingentity;
				return isDurative(this.mob);
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
			((OceanizedIllusionerEntity) rangedAttackMob).entityData.set(SHOOT, false);
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
					((OceanizedIllusionerEntity) rangedAttackMob).entityData.set(SHOOT, false);
					return;
				}
				((OceanizedIllusionerEntity) rangedAttackMob).entityData.set(SHOOT, true);
				float f = (float) Math.sqrt(d0) / this.attackRadius;
				float f1 = Mth.clamp(f, 0.1F, 1.0F);
				this.rangedAttackMob.performRangedAttack(this.target, f1);
				this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
			} else if (this.attackTime < 0) {
				this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) this.attackRadius, (double) this.attackIntervalMin, (double) this.attackIntervalMax));
			} else
				((OceanizedIllusionerEntity) rangedAttackMob).entityData.set(SHOOT, false);
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
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.ambient"));
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.death"));
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.DROWN))
			return false;
		boolean flag = SwapWithIllusionProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		int num = (int) GetIllusionNumProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ());
		float scale = Math.max(1 - num * 0.1f, 0.2f);
		if (flag){
			return super.hurt(source, amount * scale * 0.5f);
		}
		return super.hurt(source, amount * scale);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		InitMyRavagerProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataspellP", this.entityData.get(DATA_spellP));
		compound.putInt("DatamirrorP", this.entityData.get(DATA_mirrorP));
		compound.putInt("Dataduration", this.entityData.get(DATA_duration));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataspellP"))
			this.entityData.set(DATA_spellP, compound.getInt("DataspellP"));
		if (compound.contains("DatamirrorP"))
			this.entityData.set(DATA_mirrorP, compound.getInt("DatamirrorP"));
		if (compound.contains("Dataduration"))
			this.entityData.set(DATA_duration, compound.getInt("Dataduration"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		IllusionerSkillsProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
    public void setHealth(float pHealth){
        if (this.isPassenger()){
            float curHealth = this.getHealth();
            float maxHealth = this.getMaxHealth();
            float deletion = this.getHealth() - pHealth;
            float newHealth = Math.max(curHealth - 1, curHealth - deletion);
            if (curHealth >= maxHealth * 0.5){
                super.setHealth(Math.max(newHealth, maxHealth * 0.5f));
            } else {
                super.setHealth(newHealth);
            }
        } else {
            super.setHealth(pHealth);
        }
    }

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public void performRangedAttack(LivingEntity target, float flval) {
		ShotOceanArrowEntity.shoot(this, target);
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.18);
		builder = builder.add(Attributes.MAX_HEALTH, 108);
		builder = builder.add(Attributes.ARMOR, 6);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 9);
		builder = builder.add(Attributes.FOLLOW_RANGE, 28);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.75);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if (this.isAttacking() && event.isMoving()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_illusioner.aggre_move"));
			}
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.1F && event.getLimbSwingAmount() < 0.1F))
) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_illusioner.aggre_move"));
			}
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.oceanized_illusioner.die"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_illusioner.idle"));
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
		if ((this.swinging || this.entityData.get(SHOOT)) && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.oceanized_illusioner.attack"));
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
			this.remove(OceanizedIllusionerEntity.RemovalReason.KILLED);
			this.dropExperience();
			Level level = this.level();
        	AABB aabb = new AABB(this.position().add(24,24,24), this.position().add(-24,-24,-24));
        	List<OceanIllusionEntity> illusions = level.getEntitiesOfClass(OceanIllusionEntity.class, aabb);
        	illusions.forEach(e->{
            e.remove(RemovalReason.DISCARDED);
        	});
		}
	}

	public boolean isAttacking(){
        LivingEntity target = this.getTarget();
        return target!= null && target.isAlive();
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
