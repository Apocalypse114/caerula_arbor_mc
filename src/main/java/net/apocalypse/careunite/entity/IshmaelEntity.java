
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.Goal;
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
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.IshmaelTicksProcedure;
import net.apocalypse.careunite.procedures.EmployWildIshmaelProcedure;
import net.apocalypse.careunite.procedures.CanIshmaelActionProcedure;
import net.apocalypse.careunite.procedures.IshmaelAttackProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

import net.apocalypse.careunite.CaerulaArborMod;

import javax.annotation.Nullable;

import java.util.EnumSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;
import java.util.List;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;

public class IshmaelEntity extends TamableAnimal implements RangedAttackMob, GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(IshmaelEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(IshmaelEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(IshmaelEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_TARGET_ID = SynchedEntityData.defineId(IshmaelEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_DURATION = SynchedEntityData.defineId(IshmaelEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_SKILL_P = SynchedEntityData.defineId(IshmaelEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_CHARGE_P = SynchedEntityData.defineId(IshmaelEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_CHARGED_SPELL = SynchedEntityData.defineId(IshmaelEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_MAX_CHARGE_P = SynchedEntityData.defineId(IshmaelEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_BLINK_T = SynchedEntityData.defineId(IshmaelEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_BEAM_RENDER_T = SynchedEntityData.defineId(IshmaelEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_DX_1 = SynchedEntityData.defineId(IshmaelEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_DX_2 = SynchedEntityData.defineId(IshmaelEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_DX_3 = SynchedEntityData.defineId(IshmaelEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_DY_1 = SynchedEntityData.defineId(IshmaelEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_DY_2 = SynchedEntityData.defineId(IshmaelEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_DY_3 = SynchedEntityData.defineId(IshmaelEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_DZ_1 = SynchedEntityData.defineId(IshmaelEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_DZ_2 = SynchedEntityData.defineId(IshmaelEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_DZ_3 = SynchedEntityData.defineId(IshmaelEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public IshmaelEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.ISHMAEL.get(), world);
	}

	public IshmaelEntity(EntityType<IshmaelEntity> type, Level world) {
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
		this.entityData.define(TEXTURE, "ishmael");
		this.entityData.define(DATA_TARGET_ID, 0);
		this.entityData.define(DATA_DURATION, 0);
		this.entityData.define(DATA_SKILL_P, 100);
		this.entityData.define(DATA_CHARGE_P, 50);
		this.entityData.define(DATA_CHARGED_SPELL, 3);
		this.entityData.define(DATA_MAX_CHARGE_P, 0);
		this.entityData.define(DATA_BLINK_T, 0);
		this.entityData.define(DATA_BEAM_RENDER_T, 0);
		this.entityData.define(DATA_DX_1, 0);
		this.entityData.define(DATA_DX_2, 0);
		this.entityData.define(DATA_DX_3, 0);
		this.entityData.define(DATA_DY_1, 0);
		this.entityData.define(DATA_DY_2, 0);
		this.entityData.define(DATA_DY_3, 0);
		this.entityData.define(DATA_DZ_1, 0);
		this.entityData.define(DATA_DZ_2, 0);
		this.entityData.define(DATA_DZ_3, 0);
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
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Monster.class, true, false) {
			@Override
			public boolean canUse() {
				double x = IshmaelEntity.this.getX();
				double y = IshmaelEntity.this.getY();
				double z = IshmaelEntity.this.getZ();
				Entity entity = IshmaelEntity.this;
				Level world = IshmaelEntity.this.level();
				return super.canUse() && CanIshmaelActionProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = IshmaelEntity.this.getX();
				double y = IshmaelEntity.this.getY();
				double z = IshmaelEntity.this.getZ();
				Entity entity = IshmaelEntity.this;
				Level world = IshmaelEntity.this.level();
				return super.canContinueToUse() && CanIshmaelActionProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = IshmaelEntity.this.getX();
				double y = IshmaelEntity.this.getY();
				double z = IshmaelEntity.this.getZ();
				Entity entity = IshmaelEntity.this;
				Level world = IshmaelEntity.this.level();
				return super.canUse() && CanIshmaelActionProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = IshmaelEntity.this.getX();
				double y = IshmaelEntity.this.getY();
				double z = IshmaelEntity.this.getZ();
				Entity entity = IshmaelEntity.this;
				Level world = IshmaelEntity.this.level();
				return super.canContinueToUse() && CanIshmaelActionProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = IshmaelEntity.this.getX();
				double y = IshmaelEntity.this.getY();
				double z = IshmaelEntity.this.getZ();
				Entity entity = IshmaelEntity.this;
				Level world = IshmaelEntity.this.level();
				return super.canUse() && CanIshmaelActionProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = IshmaelEntity.this.getX();
				double y = IshmaelEntity.this.getY();
				double z = IshmaelEntity.this.getZ();
				Entity entity = IshmaelEntity.this;
				Level world = IshmaelEntity.this.level();
				return super.canContinueToUse() && CanIshmaelActionProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(5, new FloatGoal(this));
		this.goalSelector.addGoal(1, new IshmaelEntity.RangedAttackGoal(this, 1.25, 20, 16f) {
			@Override
			public boolean canContinueToUse() {
				return this.canUse();
			}
		});
	}

	public class RangedAttackGoal extends Goal {
		private final IshmaelEntity mob;
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
				this.mob = (IshmaelEntity) p_25773_;
				this.speedModifier = p_25774_;
				this.attackIntervalMin = p_25775_;
				this.attackIntervalMax = p_25776_;
				this.attackRadius = p_25777_;
				this.attackRadiusSqr = p_25777_ * p_25777_;
				this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
			}
		}

		public boolean canUse() {
			LivingEntity livingentity = this.mob.getTarget();
			if (livingentity != null && livingentity.isAlive()) {
				this.target = livingentity;
				return CanIshmaelActionProcedure.execute(this.mob) 
					&& !livingentity.hasEffect(CaerulaArborModMobEffects.TRAPPED.get()) && !livingentity.hasEffect(CaerulaArborModMobEffects.INVULNERABLE.get());
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
			((IshmaelEntity) rangedAttackMob).entityData.set(SHOOT, false);
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
					((IshmaelEntity) rangedAttackMob).entityData.set(SHOOT, false);
					return;
				}
				((IshmaelEntity) rangedAttackMob).entityData.set(SHOOT, true);
				float f = (float) Math.sqrt(d0) / this.attackRadius;
				float f1 = Mth.clamp(f, 0.1F, 1.0F);
				this.rangedAttackMob.performRangedAttack(this.target, f1);
				this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
			} else if (this.attackTime < 0) {
				this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) this.attackRadius, (double) this.attackIntervalMin, (double) this.attackIntervalMax));
			} else
				((IshmaelEntity) rangedAttackMob).entityData.set(SHOOT, false);
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
	public SoundEvent getHurtSound(DamageSource ds) {
		return SoundEvents.GENERIC_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.GENERIC_DEATH;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.getDirectEntity() instanceof ThrownPotion || source.getDirectEntity() instanceof AreaEffectCloud)
			return false;
		if (source.is(DamageTypes.LIGHTNING_BOLT))
			return false;
		if (source.getEntity() instanceof LivingEntity living && living.hasEffect(CaerulaArborModMobEffects.TRAPPED.get())) amount *= 0.5f;
		return super.hurt(source, amount);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataTARGET_ID", this.entityData.get(DATA_TARGET_ID));
		compound.putInt("DataDURATION", this.entityData.get(DATA_DURATION));
		compound.putInt("DataSKILL_P", this.entityData.get(DATA_SKILL_P));
		compound.putInt("DataCHARGE_P", this.entityData.get(DATA_CHARGE_P));
		compound.putInt("DataCHARGED_SPELL", this.entityData.get(DATA_CHARGED_SPELL));
		compound.putInt("DataMAX_CHARGE_P", this.entityData.get(DATA_MAX_CHARGE_P));
		compound.putInt("DataBLINK_T", this.entityData.get(DATA_BLINK_T));
		compound.putInt("DataBEAM_RENDER_T", this.entityData.get(DATA_BEAM_RENDER_T));
		compound.putInt("DataDX_1", this.entityData.get(DATA_DX_1));
		compound.putInt("DataDX_2", this.entityData.get(DATA_DX_2));
		compound.putInt("DataDX_3", this.entityData.get(DATA_DX_3));
		compound.putInt("DataDY_1", this.entityData.get(DATA_DY_1));
		compound.putInt("DataDY_2", this.entityData.get(DATA_DY_2));
		compound.putInt("DataDY_3", this.entityData.get(DATA_DY_3));
		compound.putInt("DataDZ_1", this.entityData.get(DATA_DZ_1));
		compound.putInt("DataDZ_2", this.entityData.get(DATA_DZ_2));
		compound.putInt("DataDZ_3", this.entityData.get(DATA_DZ_3));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataTARGET_ID"))
			this.entityData.set(DATA_TARGET_ID, compound.getInt("DataTARGET_ID"));
		if (compound.contains("DataDURATION"))
			this.entityData.set(DATA_DURATION, compound.getInt("DataDURATION"));
		if (compound.contains("DataSKILL_P"))
			this.entityData.set(DATA_SKILL_P, compound.getInt("DataSKILL_P"));
		if (compound.contains("DataCHARGE_P"))
			this.entityData.set(DATA_CHARGE_P, compound.getInt("DataCHARGE_P"));
		if (compound.contains("DataCHARGED_SPELL"))
			this.entityData.set(DATA_CHARGED_SPELL, compound.getInt("DataCHARGED_SPELL"));
		if (compound.contains("DataMAX_CHARGE_P"))
			this.entityData.set(DATA_MAX_CHARGE_P, compound.getInt("DataMAX_CHARGE_P"));
		if (compound.contains("DataBLINK_T"))
			this.entityData.set(DATA_BLINK_T, compound.getInt("DataBLINK_T"));
		if (compound.contains("DataBEAM_RENDER_T"))
			this.entityData.set(DATA_BEAM_RENDER_T, compound.getInt("DataBEAM_RENDER_T"));
		if (compound.contains("DataDX_1"))
			this.entityData.set(DATA_DX_1, compound.getInt("DataDX_1"));
		if (compound.contains("DataDX_2"))
			this.entityData.set(DATA_DX_2, compound.getInt("DataDX_2"));
		if (compound.contains("DataDX_3"))
			this.entityData.set(DATA_DX_3, compound.getInt("DataDX_3"));
		if (compound.contains("DataDY_1"))
			this.entityData.set(DATA_DY_1, compound.getInt("DataDY_1"));
		if (compound.contains("DataDY_2"))
			this.entityData.set(DATA_DY_2, compound.getInt("DataDY_2"));
		if (compound.contains("DataDY_3"))
			this.entityData.set(DATA_DY_3, compound.getInt("DataDY_3"));
		if (compound.contains("DataDZ_1"))
			this.entityData.set(DATA_DZ_1, compound.getInt("DataDZ_1"));
		if (compound.contains("DataDZ_2"))
			this.entityData.set(DATA_DZ_2, compound.getInt("DataDZ_2"));
		if (compound.contains("DataDZ_3"))
			this.entityData.set(DATA_DZ_3, compound.getInt("DataDZ_3"));
	}

	@Override
	public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
		ItemStack itemstack = sourceentity.getItemInHand(hand);
		InteractionResult retval = InteractionResult.sidedSuccess(this.level().isClientSide());
		super.mobInteract(sourceentity, hand);
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();
		Entity entity = this;
		Level world = this.level();
		return EmployWildIshmaelProcedure.execute(world, x, y, z, entity, sourceentity);
	}

	public int timeTillNextBlink = 40;

	@Override
	public void tick(){
		super.tick();
		timeTillNextBlink--;
		if (timeTillNextBlink <= 0){
			this.entityData.set(DATA_BLINK_T, 6);
			timeTillNextBlink = Mth.nextInt(this.getRandom(), 40, 60);
		}
	}

	@Override
	public void baseTick() {
		super.baseTick();
		IshmaelTicksProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		AttributeInstance ins = this.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get());
		if(ins != null)
			ins.setBaseValue(ins.getBaseValue() + 40);
		return retval;
	}


	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	public static SoundEvent shootSound = null;

	@Override
	public void performRangedAttack(LivingEntity target, float flval) {
		if (shootSound == null) shootSound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:indigo_shoot"));
		float p = Mth.nextFloat(this.getRandom(), 0.9f, 1.1f);
		if (!this.level().isClientSide()) {
			this.level().playSound(null, this.blockPosition(), shootSound, SoundSource.NEUTRAL, 2, p);
		} else {
			this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), shootSound, SoundSource.NEUTRAL, 2, p, false);
		}
		CaerulaArborMod.queueServerWork(8, () -> {
			IshmaelAttackProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this, target);
		});
	}

	public static void init() {
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
		return ageable;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return false;
	}


	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 140);
		builder = builder.add(Attributes.ARMOR, 6);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 12);
		builder = builder.add(Attributes.FOLLOW_RANGE, 27);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
		//builder = builder.add(CaerulaArborModAttributes.MAGIC_RESISTANCE.get(), 45);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.ishmael.die"));
		}
		if (inSkill()) return event.setAndContinue(RawAnimation.begin().thenLoop("animation.ishmael.skill"));
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.1F && event.getLimbSwingAmount() < 0.1F))
) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.ishmael.walk"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.ishmael.idle"));
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
		if (this.swinging && this.lastSwing + 23L <= level().getGameTime()) {
			this.swinging = false;
		}
		if ((this.swinging || this.entityData.get(SHOOT)) && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			if(this.level().getRandom().nextFloat() < 0.5f) return event.setAndContinue(RawAnimation.begin().thenPlay("animation.ishmael.attack1"));
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.ishmael.attack"));
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
			this.remove(IshmaelEntity.RemovalReason.KILLED);
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
		data.add(new AnimationController<>(this, "attacking", 1, this::attackingPredicate));
		data.add(new AnimationController<>(this, "procedure", 1, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	public List<Vec3> getStartPos(){
		return List.of(
			new Vec3(
				this.entityData.get(DATA_DX_1) * 0.1,this.entityData.get(DATA_DY_1) * 0.1,this.entityData.get(DATA_DZ_1) * 0.1),
			new Vec3(
				this.entityData.get(DATA_DX_2) * 0.1,this.entityData.get(DATA_DY_2) * 0.1,this.entityData.get(DATA_DZ_2) * 0.1),
			new Vec3(
				this.entityData.get(DATA_DX_3) * 0.1,this.entityData.get(DATA_DY_3) * 0.1,this.entityData.get(DATA_DZ_3) * 0.1)
			);
	}
	public boolean inSkill(){
		return this.entityData.get(DATA_SKILL_P) < 0;
	}
	public int charge(){
		return this.entityData.get(DATA_CHARGED_SPELL);
	}
	public Entity getAim(){
		int id = this.entityData.get(DATA_TARGET_ID);
		return this.level().getEntity(id);
	}
	public int getBeamTick(){
		return this.entityData.get(DATA_BEAM_RENDER_T);
	}
	public boolean shouldBlink(){
		return this.entityData.get(DATA_BLINK_T) > 0 || this.inSkill();
	}
}
