
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

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
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

import net.apocalypse.careunite.procedures.IsHollowArmorDurativeProcedure;
import net.apocalypse.careunite.procedures.HollowDoHurtTargetProcedure;
import net.apocalypse.careunite.procedures.HollowArmorTickProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.AlchemyCreation;
import net.apocalypse.careunite.init.CaerulaArborModSounds;
import net.apocalypse.careunite.CaerulaArborMod;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.apocalypse.careunite.init.CaerulaArborModItems;

public class HollowArmorEntity extends AlchemyCreation implements GeoEntity {
	public static final EntityDataAccessor<Boolean> NETHERITE = SynchedEntityData.defineId(HollowArmorEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(HollowArmorEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(HollowArmorEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_BASE_HELD = SynchedEntityData.defineId(HollowArmorEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_SKILL_P = SynchedEntityData.defineId(HollowArmorEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_DURATION = SynchedEntityData.defineId(HollowArmorEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	private static final ResourceLocation TP = new ResourceLocation("caerula_arbor_reunite", "textures/entities/hollow_armor.png");
	private static final ResourceLocation T0 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/hollow_armor_0.png");
	private static final ResourceLocation T1 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/hollow_armor_1.png");
	private static final ResourceLocation T2 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/hollow_armor_2.png");
	private static final ResourceLocation T3 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/hollow_armor_3.png");

	private static final ResourceLocation TNP = new ResourceLocation("caerula_arbor_reunite", "textures/entities/hollow_net_armor.png");
	private static final ResourceLocation TN0 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/hollow_net_armor_0.png");
	private static final ResourceLocation TN1 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/hollow_net_armor_1.png");
	private static final ResourceLocation TN2 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/hollow_net_armor_2.png");
	private static final ResourceLocation TN3 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/hollow_net_armor_3.png");

	private static final ResourceLocation LP = new ResourceLocation("caerula_arbor_reunite", "textures/entities/hollowarmor_lit.png");
	private static final ResourceLocation L0 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/hollowarmor_lit_0.png");
	private static final ResourceLocation L1 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/hollowarmor_lit_1.png");
	private static final ResourceLocation L2 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/hollowarmor_lit_2.png");
	private static final ResourceLocation L3 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/hollowarmor_lit_3.png");

	public HollowArmorEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.HOLLOW_ARMOR.get(), world);
	}

	public HollowArmorEntity(EntityType<HollowArmorEntity> type, Level world) {
		super(type, world);
		xpReward = 9;
		setNoAi(false);
		setMaxUpStep(1f);
		setPersistenceRequired();
		setBaseHeld(4);
	}

	public int extractionCooldown(){
    	return 120;
    }

    public int injectionType(){
    	return 1;
    }

	public boolean inSkill(){
		return !isStunned() && entityData.get(DATA_SKILL_P) > 0;
	}

	public void setStunned(boolean st){
		if (st) this.setAnimation("drop");
		else this.setAnimation("revive");
		super.setStunned(st);
	}

	public int maxBase(){
		return isNetherite() ? 8: 6;
	}

	public boolean isFoil(){
		return entityData.get(DATA_BASE_HELD) >= 8;
	}

	@Override
	public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
		ItemStack itemstack = sourceentity.getItemInHand(hand);
		Level world = this.level();
		if (!isNetherite() && sourceentity.isCreative() && sourceentity.getItemInHand(hand).is(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)){
			this.setNetherite();
			return InteractionResult.sidedSuccess(this.level().isClientSide());
		}
		return super.mobInteract(sourceentity, hand);
	}

	public boolean canOverflow(){
		return true;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(NETHERITE, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "hollow_armor");
		this.entityData.define(DATA_BASE_HELD, 4);
		this.entityData.define(DATA_SKILL_P, -200);
		this.entityData.define(DATA_DURATION, 0);
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public ResourceLocation getTexture() {
		if (isNetherite()){
			if (isStunned()) return TNP;
			int b = this.entityData.get(DATA_BASE_HELD);
			if (b <= 1) return TN0;
			if (b <= 3) return TN1;
			if (b <= 5) return TN2;
			return TN3;
		}
		if (isStunned()) return TP;
		int b = this.entityData.get(DATA_BASE_HELD);
		if (b <= 1) return T0;
		if (b <= 3) return T1;
		if (b <= 5) return T2;
		return T3;
	}

	public ResourceLocation getGlowmask() {
		if (isStunned()) return LP;
		int b = this.entityData.get(DATA_BASE_HELD);
		if (b <= 1) return L0;
		if (b <= 3) return L1;
		if (b <= 5) return L2;
		return L3;
	}


	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return 9;
			}

			@Override
			public boolean canUse() {
				HollowArmorEntity entity = HollowArmorEntity.this;
				return super.canUse() && IsHollowArmorDurativeProcedure.execute(entity) && !entity.inSkill();
			}

			@Override
			public boolean canContinueToUse() {
				HollowArmorEntity entity = HollowArmorEntity.this;
				return super.canContinueToUse() && IsHollowArmorDurativeProcedure.execute(entity) && !entity.inSkill();
			}

		});
		this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				Entity entity = HollowArmorEntity.this;
				return super.canUse() && IsHollowArmorDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				Entity entity = HollowArmorEntity.this;
				return super.canContinueToUse() && IsHollowArmorDurativeProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				Entity entity = HollowArmorEntity.this;
				return super.canUse() && IsHollowArmorDurativeProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				Entity entity = HollowArmorEntity.this;
				return super.canContinueToUse() && IsHollowArmorDurativeProcedure.execute(entity);
			}
		});
		//this.goalSelector.addGoal(5, new FloatGoal(this));
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
		return SoundEvents.IRON_GOLEM_HURT;
	}

	@Override
	public boolean doHurtTarget(Entity pEntity){
		this.entityData.set(DATA_DURATION, 29);
		this.level().playSound(null, this.blockPosition(), CaerulaArborModSounds.HOLLOW_ARMOR_PRE.get(), SoundSource.NEUTRAL, 1.25f,
			Mth.nextFloat(this.getRandom(), 0.8f, 1.2f));

		CaerulaArborMod.queueServerWork(14, ()->{
			if (!this.isStunned() && !this.isDeadOrDying())
				HollowDoHurtTargetProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), pEntity, this);
			}
		);
		return true;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		
		if (source.is(DamageTypes.DROWN))
			return false;
		if (!source.is(DamageTypeTags.BYPASSES_EFFECTS) 
			&& !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)
			&& !source.is(DamageTypeTags.BYPASSES_RESISTANCE)){
			float mult = 1 - getBaseHeld() * 0.15f;
			mult = Mth.clamp(mult, isNetherite() ? 0: 0.1f, 1);
			amount *= mult;
		}
		return super.hurt(source, amount);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		//compound.putString("Texture", this.getTexture());
		compound.putInt("DataBASE_HELD", this.entityData.get(DATA_BASE_HELD));
		compound.putInt("DataSKILL_P", this.entityData.get(DATA_SKILL_P));
		compound.putInt("DataDURATION", this.entityData.get(DATA_DURATION));
		compound.putBoolean("netherite", entityData.get(NETHERITE));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
//		if (compound.contains("Texture"))
//			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataBASE_HELD"))
			this.entityData.set(DATA_BASE_HELD, compound.getInt("DataBASE_HELD"));
		if (compound.contains("DataSKILL_P"))
			this.entityData.set(DATA_SKILL_P, compound.getInt("DataSKILL_P"));
		if (compound.contains("DataDURATION"))
			this.entityData.set(DATA_DURATION, compound.getInt("DataDURATION"));
		if (compound.contains("netherite")){
			this.entityData.set(NETHERITE, compound.getBoolean("netherite"));
		}
	}

	@Override
	public void baseTick() {
		super.baseTick();
		HollowArmorTickProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	public boolean isNetherite(){
		return entityData.get(NETHERITE);
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
		ItemStack stack = pPlayer.getItemInHand(InteractionHand.OFF_HAND);
		if (!stack.isEmpty() && stack.is(Items.NETHERITE_INGOT)){
			this.setNetherite();
			stack.shrink(1);
		}
	}

	private void setNetherite(){
		if (!isNetherite()){
			entityData.set(NETHERITE, true);
			multipAttr(Attributes.MAX_HEALTH, 1.5);
			multipAttr(Attributes.ATTACK_DAMAGE, 1.3);
			setAttr(Attributes.ARMOR_TOUGHNESS, 6);
			this.setHealth(this.getMaxHealth());
			this.level().playSound(null, this.blockPosition(), SoundEvents.SMITHING_TABLE_USE, SoundSource.HOSTILE, 
            	1, Mth.nextFloat(this.getRandom(), 0.8f, 1.2f));
		}
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
		builder = builder.add(Attributes.MAX_HEALTH, 72);
		builder = builder.add(Attributes.ARMOR, 10);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 9);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.isDeadOrDying()) {
			return event.setAndContinue(RawAnimation.begin().thenPlay("die"));
		}
		if (this.animationprocedure.equals("empty")) {
			if (this.isStunned()) return event.setAndContinue(RawAnimation.begin().thenLoop("overflow"));
			if (this.entityData.get(DATA_SKILL_P) > 0) return event.setAndContinue(RawAnimation.begin().thenLoop("kaigua"));
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))
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
		if (this.swinging && this.lastSwing + 29L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.entityData.get(DATA_DURATION) > 0 && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
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
