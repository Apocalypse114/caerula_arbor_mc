
package net.apocalypse.careunite.entity;

import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.GeoEntity;

import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
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

import net.apocalypse.careunite.procedures.IsharmlaToHumanProcedure;
import net.apocalypse.careunite.procedures.IsharmlaToMonsterProcedure;
import net.apocalypse.careunite.procedures.IsharmlaSkillsProcedure;
import net.apocalypse.careunite.procedures.IsharmlaInitProcedure;
import net.apocalypse.careunite.procedures.IsIsharmlaMonsterProcedure;
import net.apocalypse.careunite.procedures.IsIsharmlaDurativeProcedure;
import net.apocalypse.careunite.procedures.IsharmlaDropRelicProcedure;
import net.apocalypse.careunite.procedures.DistributeIsharmlaTearProcedure;
import net.apocalypse.careunite.procedures.AddLivingBarrierProcedure;
import net.apocalypse.careunite.procedures.IsharmlaDoHurtTargetProcedure;
import net.apocalypse.careunite.procedures.AddLayeredBarrierProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.init.CaerulaArborModSounds;

import javax.annotation.Nullable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.Difficulty;

public class IsharmlaEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(IsharmlaEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(IsharmlaEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(IsharmlaEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Boolean> DATA_IS_MONSTER = SynchedEntityData.defineId(IsharmlaEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_SKILLP_1 = SynchedEntityData.defineId(IsharmlaEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_SKILLP_2 = SynchedEntityData.defineId(IsharmlaEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_DURATION = SynchedEntityData.defineId(IsharmlaEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_RECORDED_HEALTH = SynchedEntityData.defineId(IsharmlaEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_ABSORPTION = SynchedEntityData.defineId(IsharmlaEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_HEAL_P = SynchedEntityData.defineId(IsharmlaEntity.class, EntityDataSerializers.INT);
	private boolean IS_ANGERED;
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.YELLOW, ServerBossEvent.BossBarOverlay.NOTCHED_6);

	//public static final SoundEvent SKADI_HIT = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:skadi_hit"));
	
	public IsharmlaEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.ISHARMLA.get(), world);
	}

	public IsharmlaEntity(EntityType<IsharmlaEntity> type, Level world) {
		super(type, world);
		xpReward = 64;
		setNoAi(false);
		setMaxUpStep(1.5f);
		setPersistenceRequired();
		IS_ANGERED = false;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "isharmla_corrupted_heart");
		this.entityData.define(DATA_IS_MONSTER, false);
		this.entityData.define(DATA_SKILLP_1, 400);
		this.entityData.define(DATA_SKILLP_2, 240);
		this.entityData.define(DATA_DURATION, 60);
		this.entityData.define(DATA_RECORDED_HEALTH, 1000);
		this.entityData.define(DATA_ABSORPTION, 60);
		this.entityData.define(DATA_HEAL_P, 60);
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
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 576;
			}

			@Override
			public boolean canUse() {
				double x = IsharmlaEntity.this.getX();
				double y = IsharmlaEntity.this.getY();
				double z = IsharmlaEntity.this.getZ();
				Entity entity = IsharmlaEntity.this;
				Level world = IsharmlaEntity.this.level();
				return super.canUse() && IsIsharmlaMonsterProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = IsharmlaEntity.this.getX();
				double y = IsharmlaEntity.this.getY();
				double z = IsharmlaEntity.this.getZ();
				Entity entity = IsharmlaEntity.this;
				Level world = IsharmlaEntity.this.level();
				return super.canContinueToUse() && IsIsharmlaMonsterProcedure.execute(entity);
			}

		});
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = IsharmlaEntity.this.getX();
				double y = IsharmlaEntity.this.getY();
				double z = IsharmlaEntity.this.getZ();
				Entity entity = IsharmlaEntity.this;
				Level world = IsharmlaEntity.this.level();
				return super.canUse() && IsIsharmlaDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = IsharmlaEntity.this.getX();
				double y = IsharmlaEntity.this.getY();
				double z = IsharmlaEntity.this.getZ();
				Entity entity = IsharmlaEntity.this;
				Level world = IsharmlaEntity.this.level();
				return super.canContinueToUse() && IsIsharmlaDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = IsharmlaEntity.this.getX();
				double y = IsharmlaEntity.this.getY();
				double z = IsharmlaEntity.this.getZ();
				Entity entity = IsharmlaEntity.this;
				Level world = IsharmlaEntity.this.level();
				return !isMonster() && super.canUse() && IsIsharmlaDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = IsharmlaEntity.this.getX();
				double y = IsharmlaEntity.this.getY();
				double z = IsharmlaEntity.this.getZ();
				Entity entity = IsharmlaEntity.this;
				Level world = IsharmlaEntity.this.level();
				return super.canContinueToUse() && IsIsharmlaDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(5, new FloatGoal(this));
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
	public SoundEvent getAmbientSound() {
		if (isMonster()) CaerulaArborModSounds.SEABORN_MASSIVE_AMBIENT.get();
		return super.getAmbientSound();
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		if (isMonster()) return CaerulaArborModSounds.SEABORN_MASSIVE_HURT.get();
		return CaerulaArborModSounds.SKADI_HIT.get();
	}

	@Override
	public SoundEvent getDeathSound() {
		return CaerulaArborModSounds.SEABORN_MASSIVE_DEATH.get();
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.DROWN) || source.is(DamageTypes.IN_WALL)
)
			return false;
		return super.hurt(source, amount);
	}

	@Override
	public boolean doHurtTarget(Entity pEntity){
		IsharmlaDoHurtTargetProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), pEntity, this);
		return true;
	}

	private int switchCount = 0;

	@Override
	public void die(DamageSource source) {
		if (IsharmlaToHumanProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this)){
			AddLivingBarrierProcedure.execute(this, this.getMaxHealth() * (0.25f + switchCount * 0.05f));
			if (switchCount < 15) switchCount ++;
			return;
		}
		super.die(source);
	}

	@Override
	public void setHealth(float pHealth){
		float deletion = Math.min(this.getHealth() - pHealth, this.getMaxHealth() * 0.51f);
		float newHealth = this.getHealth() - deletion;
		if (newHealth <= 0 
		&& IsharmlaToHumanProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this)){
			AddLivingBarrierProcedure.execute(this, this.getMaxHealth() * (0.25f + switchCount * 0.05f));
			if (switchCount < 15) switchCount ++;
			return;
		}
		super.setHealth(newHealth);
	}

	@Override
	public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand){
		ItemStack item = pPlayer.getMainHandItem();
		if (item.is(CaerulaArborModItems.ISHARMLA_SPAWNER.get())){
			IsharmlaToMonsterProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
			return InteractionResult.SUCCESS;
		}
		return super.mobInteract(pPlayer, pHand);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		IsharmlaInitProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putBoolean("DataIS_MONSTER", this.entityData.get(DATA_IS_MONSTER));
		compound.putInt("DataSKILLP_1", this.entityData.get(DATA_SKILLP_1));
		compound.putInt("DataSKILLP_2", this.entityData.get(DATA_SKILLP_2));
		compound.putInt("DataDURATION", this.entityData.get(DATA_DURATION));
		compound.putInt("DataRECORDED_HEALTH", this.entityData.get(DATA_RECORDED_HEALTH));
		compound.putInt("DataABSORPTION", this.entityData.get(DATA_ABSORPTION));
		compound.putInt("DataHEAL_P", this.entityData.get(DATA_HEAL_P));
		compound.putBoolean("IsAngered", IS_ANGERED);
		compound.putInt("switchCount", switchCount);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataIS_MONSTER"))
			this.entityData.set(DATA_IS_MONSTER, compound.getBoolean("DataIS_MONSTER"));
		if (compound.contains("DataSKILLP_1"))
			this.entityData.set(DATA_SKILLP_1, compound.getInt("DataSKILLP_1"));
		if (compound.contains("DataSKILLP_2"))
			this.entityData.set(DATA_SKILLP_2, compound.getInt("DataSKILLP_2"));
		if (compound.contains("DataDURATION"))
			this.entityData.set(DATA_DURATION, compound.getInt("DataDURATION"));
		if (compound.contains("DataRECORDED_HEALTH"))
			this.entityData.set(DATA_RECORDED_HEALTH, compound.getInt("DataRECORDED_HEALTH"));
		if (compound.contains("DataABSORPTION"))
			this.entityData.set(DATA_ABSORPTION, compound.getInt("DataABSORPTION"));
		if (compound.contains("DataHEAL_P"))
			this.entityData.set(DATA_HEAL_P, compound.getInt("DataHEAL_P"));
		if (compound.contains("IsAngered"))
			this.IS_ANGERED = compound.getBoolean("IsAngered");
		this.switchCount = compound.getInt("switchCount");

	}

	@Override
	public void baseTick() {
		super.baseTick();
		IsharmlaSkillsProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	public void aiStep(){
		super.aiStep();
		if (isMonster()) bossInfo.setColor(ServerBossEvent.BossBarColor.WHITE);
		else{
			bossInfo.setColor(ServerBossEvent.BossBarColor.YELLOW);
			if (this.getHealth() < this.getMaxHealth() * 0.5 && !IS_ANGERED){
				AttributeInstance inst = this.getAttribute(Attributes.ATTACK_DAMAGE);
				if (inst != null) inst.setBaseValue(inst.getBaseValue() * 1.5);
				AddLayeredBarrierProcedure.execute(this, 3);
				this.entityData.set(DATA_SKILLP_2, 1);
				DistributeIsharmlaTearProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ());
				this.IS_ANGERED = true;
			}
		}
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		if (isMonster()) return super.getDimensions(p_33597_).scale(10, 4f);
		return super.getDimensions(p_33597_);
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

	public ResourceKey<DamageType> oceankiller = ResourceKey.create(Registries.DAMAGE_TYPE, 
							new ResourceLocation("caerula_arbor_reunite:oceankiller_damage"));

	@Override
	public void remove(RemovalReason pReason){
		if(this.level().getDifficulty() != Difficulty.PEACEFUL && pReason == RemovalReason.DISCARDED){
			this.hurt(
				new DamageSource(
					this.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(oceankiller)
				),
				20
			);
			return;
		} else if (pReason == RemovalReason.KILLED && !this.isDeadOrDying()) return;	
		super.remove(pReason);
	}

	@Override
	public boolean isPushable() {
		if (isMonster()) return false;
		return true;
	}

	@Override
	protected void doPush(Entity entityIn) {
		if (isMonster()) return;
		super.doPush(entityIn);
	}


	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.18);
		builder = builder.add(Attributes.MAX_HEALTH, 320);
		builder = builder.add(Attributes.ARMOR, 12);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 16);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		builder = builder.add(CaerulaArborModAttributes.GENERAL_DEFENSE.get(), 4);
		builder = builder.add(CaerulaArborModAttributes.MAGIC_RESISTANCE.get(), 50);
		builder = builder.add(CaerulaArborModAttributes.SANITY_MODIFIER.get(), 0.01);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		boolean isM3 = isMonster();
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.05F && event.getLimbSwingAmount() < 0.05F))
) {
				if (isM3) return event.setAndContinue(RawAnimation.begin().thenLoop("animation.isharmla.idle_monster"));
				else return event.setAndContinue(RawAnimation.begin().thenLoop("animation.isharmla.move_human"));
			}
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.isharmla.die"));
			}
			if (isM3) return event.setAndContinue(RawAnimation.begin().thenLoop("animation.isharmla.idle_monster"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.isharmla.idle_human"));
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
		if (this.swinging && this.lastSwing + 42L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.isharmla.attack_monster"));
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
			IsharmlaDropRelicProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ());
			this.remove(IsharmlaEntity.RemovalReason.KILLED);
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
		data.add(new AnimationController<>(this, "attacking", 0, this::attackingPredicate));
		data.add(new AnimationController<>(this, "procedure", 0, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	public boolean isMonster(){
		return this.entityData.get(DATA_IS_MONSTER);
	}
}
