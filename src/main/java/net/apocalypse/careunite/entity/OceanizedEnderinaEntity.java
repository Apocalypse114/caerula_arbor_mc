
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
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.util.RandomSource;
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
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.procedures.IsEnderinaDurativeProcedure;
import net.apocalypse.careunite.procedures.InitEnderinaProcedure;
import net.apocalypse.careunite.procedures.EnderinaSkillsProcedure;
import net.apocalypse.careunite.procedures.EnderinaReviveingProcedure;
import net.apocalypse.careunite.procedures.DropEnderinaLootProcedure;
import net.apocalypse.careunite.procedures.
CastDragonBreathProcedure;
import net.apocalypse.careunite.procedures.EnderinareviveProcedure;
import net.apocalypse.careunite.procedures.CheckDamageSourceProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.CaerulaArborMod;

import javax.annotation.Nullable;

import java.util.EnumSet;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import java.util.Set;
import java.util.HashSet;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;

public class OceanizedEnderinaEntity extends Monster implements RangedAttackMob, GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(OceanizedEnderinaEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(OceanizedEnderinaEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(OceanizedEnderinaEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_REVIVE_TICK = SynchedEntityData.defineId(OceanizedEnderinaEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_PHASE = SynchedEntityData.defineId(OceanizedEnderinaEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_SKILL_P = SynchedEntityData.defineId(OceanizedEnderinaEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_DURATION = SynchedEntityData.defineId(OceanizedEnderinaEntity.class, EntityDataSerializers.INT);
	public static SoundEvent PRE = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:caster_pre"));
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	private boolean canDie = false;
	public String animationprocedure = "empty";
	public Set<String> crystals = new HashSet<>();
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.PINK, ServerBossEvent.BossBarOverlay.NOTCHED_10);

	public OceanizedEnderinaEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.OCEANIZED_ENDERINA.get(), world);
	}

	public OceanizedEnderinaEntity(EntityType<OceanizedEnderinaEntity> type, Level world) {
		super(type, world);
		xpReward = 128;
		setNoAi(false);
		setMaxUpStep(0.6f);
		setPersistenceRequired();
		this.moveControl = new FlyingMoveControl(this, 10, true);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "oceanized_enderina");
		this.entityData.define(DATA_REVIVE_TICK, 0);
		this.entityData.define(DATA_PHASE, 0);
		this.entityData.define(DATA_SKILL_P, 0);
		this.entityData.define(DATA_DURATION, 50);
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public void putCrystal(Entity crystal){
		this.crystals.add(crystal.getStringUUID());
	}
	public MoistEnderCrystalEntity getCrystal(String uuid){
		if (this.level() instanceof ServerLevel level){
			Entity c = level.getEntity(UUID.fromString(uuid));
			return c instanceof MoistEnderCrystalEntity cr ? cr : null;
		}
		return null;
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
		this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1, 20) {
			@Override
			protected Vec3 getPosition() {
				RandomSource random = OceanizedEnderinaEntity.this.getRandom();
				double dir_x = OceanizedEnderinaEntity.this.getX() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_y = OceanizedEnderinaEntity.this.getY() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_z = OceanizedEnderinaEntity.this.getZ() + ((random.nextFloat() * 2 - 1) * 16);
				return new Vec3(dir_x, dir_y, dir_z);
			}

			@Override
			public boolean canUse() {
				double x = OceanizedEnderinaEntity.this.getX();
				double y = OceanizedEnderinaEntity.this.getY();
				double z = OceanizedEnderinaEntity.this.getZ();
				Entity entity = OceanizedEnderinaEntity.this;
				Level world = OceanizedEnderinaEntity.this.level();
				return super.canUse() && IsEnderinaDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedEnderinaEntity.this.getX();
				double y = OceanizedEnderinaEntity.this.getY();
				double z = OceanizedEnderinaEntity.this.getZ();
				Entity entity = OceanizedEnderinaEntity.this;
				Level world = OceanizedEnderinaEntity.this.level();
				return super.canContinueToUse() && IsEnderinaDurativeProcedure.execute(entity);
			}

		});
		this.goalSelector.addGoal(3, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = OceanizedEnderinaEntity.this.getX();
				double y = OceanizedEnderinaEntity.this.getY();
				double z = OceanizedEnderinaEntity.this.getZ();
				Entity entity = OceanizedEnderinaEntity.this;
				Level world = OceanizedEnderinaEntity.this.level();
				return super.canUse() && IsEnderinaDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = OceanizedEnderinaEntity.this.getX();
				double y = OceanizedEnderinaEntity.this.getY();
				double z = OceanizedEnderinaEntity.this.getZ();
				Entity entity = OceanizedEnderinaEntity.this;
				Level world = OceanizedEnderinaEntity.this.level();
				return super.canContinueToUse() && IsEnderinaDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(1, new OceanizedEnderinaEntity.RangedAttackGoal(this, 1.25, 60, 5f) {
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
			LivingEntity livingentity = this.mob.getTarget();
			if (livingentity != null && livingentity.isAlive()) {
				this.target = livingentity;
				Entity entity = OceanizedEnderinaEntity.this;
				return IsEnderinaDurativeProcedure.execute(entity);
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
			((OceanizedEnderinaEntity) rangedAttackMob).entityData.set(SHOOT, false);
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
					((OceanizedEnderinaEntity) rangedAttackMob).entityData.set(SHOOT, false);
					return;
				}
				((OceanizedEnderinaEntity) rangedAttackMob).entityData.set(SHOOT, true);
				float f = (float) Math.sqrt(d0) / this.attackRadius;
				float f1 = Mth.clamp(f, 0.1F, 1.0F);
				this.rangedAttackMob.performRangedAttack(this.target, f1);
				this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
			} else if (this.attackTime < 0) {
				this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) this.attackRadius, (double) this.attackIntervalMin, (double) this.attackIntervalMax));
			} else
				((OceanizedEnderinaEntity) rangedAttackMob).entityData.set(SHOOT, false);
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

	public static SoundEvent HURT_SOUND = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:caster_hurt"));
	public static SoundEvent DIE_SOUND = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:caster_die"));

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return HURT_SOUND;
	}

	@Override
	public SoundEvent getDeathSound() {
		return DIE_SOUND;
	}

	@Override
	public boolean causeFallDamage(float l, float d, DamageSource source) {
		return false;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if(source.is(DamageTypes.DRAGON_BREATH)) return false;
		return super.hurt(source, amount);
	}

	@Override
	public void actuallyHurt(DamageSource source, float amount) {
		if(source.is(DamageTypes.DRAGON_BREATH)) return;
		Entity sourceEntity = source.getEntity();
		if(source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)|| source.is(DamageTypeTags.BYPASSES_EFFECTS)){
			super.actuallyHurt(source, amount);
			return;
		}
		if(sourceEntity == null && amount < this.getMaxHealth()){
			super.actuallyHurt(source, 0);
			return;
		}
		float rate = 1;
		if (isReviving()) rate = 0.25f;
		super.actuallyHurt(source, 100 * rate * (float)Math.log( 0.01f * amount + 1));
	}

	public boolean shouldSkipP2(){
		return this.getPersistentData().getBoolean("seabornForgive");
	}

	@Override
	public void die(DamageSource source) {
		if (!isReviving()) {
			this.setHealth(this.getMaxHealth() * 0.5f);
			EnderinaReviveingProcedure.execute(this);
			return;
		} else {
			if (!shouldSkipP2() && CheckDamageSourceProcedure.execute(source) && this.getPhase() <= 0){
				EnderinareviveProcedure.execute(this);
				this.canDie = false;
				return;
			} else {
				this.canDie = true;
			}
		}
		super.die(source);
	}

	@Override
	public void setHealth(float pHealth){
		float hlth = this.getHealth();
    	float mhlth = this.getMaxHealth();
    	float reduction = hlth - pHealth;
    	float finalV = reduction >= mhlth * 0.33f ? hlth - mhlth * 0.33f : hlth - reduction;
		if (finalV <= 0 && !isReviving()){
			super.setHealth(mhlth * 0.5f);
			EnderinaReviveingProcedure.execute(this);
			return;
		}
        super.setHealth(finalV);
	}

	@Override
	public void remove(RemovalReason pReason){
		if (pReason == RemovalReason.DISCARDED && this.level().getDifficulty() != Difficulty.PEACEFUL) return;
		else if (pReason == RemovalReason.KILLED && !this.isDeadOrDying()) return;
		super.remove(pReason);
	}


	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		InitEnderinaProcedure.execute(this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataREVIVE_TICK", this.entityData.get(DATA_REVIVE_TICK));
		compound.putInt("DataPHASE", this.entityData.get(DATA_PHASE));
		compound.putInt("DataSKILL_P", this.entityData.get(DATA_SKILL_P));
		compound.putInt("DataDURATION", this.entityData.get(DATA_DURATION));
		ListTag tag = new ListTag();
		this.crystals.forEach(s->{
			tag.add(StringTag.valueOf(s));
		});
		compound.put("crystals", tag);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataREVIVE_TICK"))
			this.entityData.set(DATA_REVIVE_TICK, compound.getInt("DataREVIVE_TICK"));
		if (compound.contains("DataPHASE"))
			this.entityData.set(DATA_PHASE, compound.getInt("DataPHASE"));
		if (compound.contains("DataSKILL_P"))
			this.entityData.set(DATA_SKILL_P, compound.getInt("DataSKILL_P"));
		if (compound.contains("DataDURATION"))
			this.entityData.set(DATA_DURATION, compound.getInt("DataDURATION"));
		if (compound.contains("crystals")){
			ListTag listTag = compound.getList("crystals", Tag.TAG_STRING);
			for (int i=0; i<listTag.size(); i++){
				this.crystals.add(listTag.getString(i));
        	}	
		}
			
	}

	@Override
	public void baseTick() {
		super.baseTick();
		EnderinaSkillsProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public void performRangedAttack(LivingEntity target, float flval) {
		Vec3 p = this.getLookAngle().scale(0.45).reverse();
		this.push(p.x, 0, p.z);
		Level level = this.level();
		if (!level.isClientSide()) {
			level.playSound(this, this.blockPosition(), PRE, SoundSource.HOSTILE, 2, 1);
		} else {
			level.playLocalSound(this.getX(), this.getY(), this.getZ(), PRE, SoundSource.HOSTILE, 2, 1, false);
		}
		if(getPhase() == 0){
			normalAttack(target);
		} else {
			superAttack(target);
		}
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.45);
		builder = builder.add(Attributes.MAX_HEALTH, 385);
		builder = builder.add(Attributes.ARMOR, 15);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 14);
		builder = builder.add(Attributes.FOLLOW_RANGE, 36);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 10);
		builder = builder.add(Attributes.FLYING_SPEED, 0.55);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.oceanized_enderina.die"));
			}
			if (this.isShiftKeyDown()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_enderina.revive"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.oceanized_enderina.idle"));
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
		if ((this.swinging || this.entityData.get(SHOOT)) && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.oceanized_enderina.attack"));
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
		if (!shouldSkipP2() && !this.canDie && phase() <= 0){
			this.deathTime = 0;
			this.dead = false;
			this.setHealth(1);
			EnderinareviveProcedure.execute(this);
		}
		if (this.deathTime == 40) {
			this.remove(OceanizedEnderinaEntity.RemovalReason.KILLED);
			this.dropExperience();
			DropEnderinaLootProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ());
		}
	}

	public String getSyncedAnimation() {
		return this.entityData.get(ANIMATION);
	}

	public int phase(){
		return entityData.get(DATA_PHASE);
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
		return this.entityData.get(DATA_PHASE);
	}

	private boolean isReviving(){
		return this.entityData.get(DATA_REVIVE_TICK) > 0;
	}
	private void normalAttack(LivingEntity target){
		CaerulaArborMod.queueServerWork(11, ()->{
			CastDragonBreathProcedure.execute(this.level(), this.getX(), this.getY()+2.5, this.getZ(), this, target, 0);
			CastDragonBreathProcedure.execute(this.level(), this.getX(), this.getY()+2.5, this.getZ(), this, target, 0);
			CastDragonBreathProcedure.execute(this.level(), this.getX(), this.getY()+2.5, this.getZ(), this, target, 1);
			}
		);
	}

	private void superAttack(LivingEntity target){
		CaerulaArborMod.queueServerWork(11, ()->{
			CastDragonBreathProcedure.execute(this.level(), this.getX(), this.getY()+2.5, this.getZ(), this, target, 0);
			CastDragonBreathProcedure.execute(this.level(), this.getX(), this.getY()+2.5, this.getZ(), this, target, 0);
			CastDragonBreathProcedure.execute(this.level(), this.getX(), this.getY()+2.5, this.getZ(), this, target, 0);
			CastDragonBreathProcedure.execute(this.level(), this.getX(), this.getY()+2.5, this.getZ(), this, target, 1);
			CastDragonBreathProcedure.execute(this.level(), this.getX(), this.getY()+2.5, this.getZ(), this, target, 1);
			}
		);
	}
}
