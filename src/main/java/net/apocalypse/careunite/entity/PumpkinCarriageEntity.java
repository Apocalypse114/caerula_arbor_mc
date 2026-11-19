
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

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;
import net.apocalypse.careunite.procedures.CarriageTicksProcedure;
import net.apocalypse.careunite.AlchemyCreation;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;

public class PumpkinCarriageEntity extends AlchemyCreation implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(PumpkinCarriageEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(PumpkinCarriageEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(PumpkinCarriageEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Boolean> DATA_IS_NETHERSEA = SynchedEntityData.defineId(PumpkinCarriageEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_SPRINT_T = SynchedEntityData.defineId(PumpkinCarriageEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	private static final ResourceLocation T = new ResourceLocation("caerula_arbor_reunite", "textures/entities/pumpkin_carriage.png");
	private static final ResourceLocation TN = new ResourceLocation("caerula_arbor_reunite", "textures/entities/pumpkin_carriage_nethersea.png");

	private static final ResourceLocation L = new ResourceLocation("caerula_arbor_reunite", "textures/entities/pumpkin_glowmask.png");
	private static final ResourceLocation LN = new ResourceLocation("caerula_arbor_reunite", "textures/entities/pumpkin_glowmask_nethersea.png");

	public PumpkinCarriageEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.PUMPKIN_CARRIAGE.get(), world);
	}

	public PumpkinCarriageEntity(EntityType<PumpkinCarriageEntity> type, Level world) {
		super(type, world);
		xpReward = 3;
		setNoAi(false);
		setMaxUpStep(1f);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "pumpkin_carriage");
		this.entityData.define(DATA_IS_NETHERSEA, false);
		this.entityData.define(DATA_SPRINT_T, 0);
	}

	public ResourceLocation textureLoc(){
		return entityData.get(DATA_IS_NETHERSEA) ? TN: T;
	}
	public ResourceLocation layerLoc(){
		return entityData.get(DATA_IS_NETHERSEA) ? LN: L;
	}

	public void makeNethersea(){
		if (entityData.get(DATA_IS_NETHERSEA)) return;
		multipAttr(Attributes.ATTACK_DAMAGE, 1.5);
		multipAttr(Attributes.MAX_HEALTH, 1.5);
		setAttr(CaerulaArborModAttributes.SANITY_RATE.get(), 20);
		this.setHealth(this.getMaxHealth());
		this.entityData.set(DATA_IS_NETHERSEA, true);
	}

	private void multipAttr(Attribute at, double mult){
		AttributeInstance inst = this.getAttribute(at);
		if (inst != null){
			inst.setBaseValue(inst.getBaseValue() * mult);
		}
	}
	private void setAttr(Attribute at, double ad){
		AttributeInstance inst = this.getAttribute(at);
		if (inst != null){
			inst.setBaseValue(inst.getBaseValue() + ad);
		}
	}

	public void spawnedFromInjection(BlockState state, Player pPlayer){
		super.spawnedFromInjection(state, pPlayer);
		if (state.getBlock() == CaerulaArborModBlocks.TRAIL_PUMPKING.get()){
			this.makeNethersea();
		}
	}

	public void setStunned(boolean st){
		if (st) this.setAnimation("drop");
		else this.setAnimation("revive");
		super.setStunned(st);
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
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.5, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 4;
			}
			@Override
			public boolean canUse() {
				PumpkinCarriageEntity entity = PumpkinCarriageEntity.this;
				return super.canUse() && !entity.isStunned();
			}
			@Override
			public boolean canContinueToUse() {
				PumpkinCarriageEntity entity = PumpkinCarriageEntity.this;
				return super.canContinueToUse() && !entity.isStunned();
			}

		});
		this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
		this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this){
			@Override
			public boolean canUse() {
				PumpkinCarriageEntity entity = PumpkinCarriageEntity.this;
				return super.canUse() && !entity.isStunned();
			}
			@Override
			public boolean canContinueToUse() {
				PumpkinCarriageEntity entity = PumpkinCarriageEntity.this;
				return super.canContinueToUse() && !entity.isStunned();
			}
		});
		this.goalSelector.addGoal(5, new FloatGoal(this));
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypes.FALL))
			return false;
		return super.hurt(source, amount);
	}

	public void setBlockorDrop(Block block, ItemLike item){
    	BlockPos p = this.blockPosition();
    	if (this.level().isClientSide()) return;
    	if (this.level().getLevelData().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && this.level().getBlockState(p).canBeReplaced()) {
			this.level().setBlock(p, block.defaultBlockState(), 3);
		} else {
			this.spawnAtLocation(new ItemStack(item));
		}
    }

	@Override
	public boolean doHurtTarget(Entity pEntity){
		if (this.entityData.get(DATA_SPRINT_T) <= 0){
			this.entityData.set(DATA_SPRINT_T, 19);
			return true;
		}
		return false;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putBoolean("DataIS_NETHERSEA", this.entityData.get(DATA_IS_NETHERSEA));
		compound.putInt("DataSPRINT_T", this.entityData.get(DATA_SPRINT_T));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataIS_NETHERSEA"))
			this.entityData.set(DATA_IS_NETHERSEA, compound.getBoolean("DataIS_NETHERSEA"));
		if (compound.contains("DataSPRINT_T"))
			this.entityData.set(DATA_SPRINT_T, compound.getInt("DataSPRINT_T"));
	}

	@Override
	public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
		ItemStack itemstack = sourceentity.getItemInHand(hand);
		if (sourceentity == this.getOwner()){
			sourceentity.startRiding(this);
			return InteractionResult.SUCCESS;
		}
		return super.mobInteract(sourceentity, hand);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		if (!this.isStunned()) CarriageTicksProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		else this.entityData.set(DATA_SPRINT_T, 0);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public void travel(Vec3 dir) {
		Entity entity = this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0);
		if (this.isVehicle()) {
			this.setYRot(entity.getYRot());
			this.yRotO = this.getYRot();
			this.setXRot(entity.getXRot() * 0.5F);
			this.setRot(this.getYRot(), this.getXRot());
			this.yBodyRot = entity.getYRot();
			this.yHeadRot = entity.getYRot();
			if (entity instanceof LivingEntity passenger) {
				this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
				float forward = passenger.zza;
				float strafe = 0;
				super.travel(new Vec3(strafe, 0, forward));
			}
			double d1 = this.getX() - this.xo;
			double d0 = this.getZ() - this.zo;
			float f1 = (float) Math.sqrt(d1 * d1 + d0 * d0) * 4;
			if (f1 > 1.0F)
				f1 = 1.0F;
			this.walkAnimation.setSpeed(this.walkAnimation.speed() + (f1 - this.walkAnimation.speed()) * 0.4F);
			this.walkAnimation.position(this.walkAnimation.position() + this.walkAnimation.speed());
			this.calculateEntityAnimation(true);
			return;
		}
		super.travel(dir);
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.22);
		builder = builder.add(Attributes.MAX_HEALTH, 18);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 4);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.6);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
			return event.setAndContinue(RawAnimation.begin().thenPlay("die"));
		}
		if (this.animationprocedure.equals("empty")) {
			if (this.isStunned()) return event.setAndContinue(RawAnimation.begin().thenLoop("as_item"));
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.05F && event.getLimbSwingAmount() < 0.05F))
			) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("move"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
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
		if (this.swinging && this.lastSwing + 19L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (entityData.get(DATA_SPRINT_T) > 0 && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
			event.getController().forceAnimationReset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("attack"));
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
		if (this.deathTime >= 2) {
			if (this.entityData.get(DATA_IS_NETHERSEA)) setBlockorDrop(CaerulaArborModBlocks.TRAIL_PUMPKING.get(), CaerulaArborModBlocks.TRAIL_PUMPKING.get());
			else setBlockorDrop(Blocks.CARVED_PUMPKIN, Blocks.CARVED_PUMPKIN);
			this.remove(PumpkinCarriageEntity.RemovalReason.KILLED);
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
}
