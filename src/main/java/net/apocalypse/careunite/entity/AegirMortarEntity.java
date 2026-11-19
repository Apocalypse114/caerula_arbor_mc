
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import net.apocalypse.careunite.procedures.MortarOutputGunpowderProcedure;
import net.apocalypse.careunite.procedures.MobAliveProcedure;
import net.apocalypse.careunite.procedures.InteractFireMortarProcedure;
import net.apocalypse.careunite.procedures.AegirMortarTicksProcedure;
import net.apocalypse.careunite.procedures.AegirMortarInitProcedure;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

import javax.annotation.Nullable;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.minecraft.world.item.Items;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.AABB;
import java.util.List;
import net.minecraft.world.phys.Vec3;

public class AegirMortarEntity extends Mob implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(AegirMortarEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(AegirMortarEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(AegirMortarEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_OPEN_TICK = SynchedEntityData.defineId(AegirMortarEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_CLOSE_TICK = SynchedEntityData.defineId(AegirMortarEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_WORKING = SynchedEntityData.defineId(AegirMortarEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_FIRE_COOLDOWN = SynchedEntityData.defineId(AegirMortarEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_COMMON_AMMO = SynchedEntityData.defineId(AegirMortarEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_SUPER_AMMO = SynchedEntityData.defineId(AegirMortarEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_TARGET_ANGLE = SynchedEntityData.defineId(AegirMortarEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_CURRENT_ANGLE = SynchedEntityData.defineId(AegirMortarEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_START_TICK = SynchedEntityData.defineId(AegirMortarEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_AUTO = SynchedEntityData.defineId(AegirMortarEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_FIRE_MODE = SynchedEntityData.defineId(AegirMortarEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public AegirMortarEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CaerulaArborModEntities.AEGIR_MORTAR.get(), world);
	}

	public AegirMortarEntity(EntityType<AegirMortarEntity> type, Level world) {
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
		this.entityData.define(TEXTURE, "aegir_mortar");
		this.entityData.define(DATA_OPEN_TICK, 0);
		this.entityData.define(DATA_CLOSE_TICK, 0);
		this.entityData.define(DATA_WORKING, true);
		this.entityData.define(DATA_FIRE_COOLDOWN, 60);
		this.entityData.define(DATA_COMMON_AMMO, 0);
		this.entityData.define(DATA_SUPER_AMMO, 0);
		this.entityData.define(DATA_TARGET_ANGLE, 0);
		this.entityData.define(DATA_CURRENT_ANGLE, 0);
		this.entityData.define(DATA_START_TICK, 60);
		this.entityData.define(DATA_AUTO, true);
		this.entityData.define(DATA_FIRE_MODE, 0);
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public String getTexture() {
		return this.entityData.get(TEXTURE);
	}

	@Override
	public boolean canCollideWith(Entity entity) {
		return true;
	}

	@Override
	public boolean canBeCollidedWith() {
		return MobAliveProcedure.execute(this);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();

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
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.armor_stand.hit"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.armor_stand.break"));
	}

	@Override
	public boolean hurt(DamageSource pSource, float pAmount){
		if (pSource.getEntity() == null){
			return false;
		}
		return super.hurt(pSource, pAmount);
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		MortarOutputGunpowderProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		AegirMortarInitProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataOPEN_TICK", this.entityData.get(DATA_OPEN_TICK));
		compound.putInt("DataCLOSE_TICK", this.entityData.get(DATA_CLOSE_TICK));
		compound.putBoolean("DataWORKING", this.entityData.get(DATA_WORKING));
		compound.putInt("DataFIRE_COOLDOWN", this.entityData.get(DATA_FIRE_COOLDOWN));
		compound.putInt("DataCOMMON_AMMO", this.entityData.get(DATA_COMMON_AMMO));
		compound.putInt("DataSUPER_AMMO", this.entityData.get(DATA_SUPER_AMMO));
		compound.putInt("DataTARGET_ANGLE", this.entityData.get(DATA_TARGET_ANGLE));
		compound.putInt("DataCURRENT_ANGLE", this.entityData.get(DATA_CURRENT_ANGLE));
		compound.putInt("DataSTART_TICK", this.entityData.get(DATA_START_TICK));
		compound.putBoolean("DataAUTO", this.entityData.get(DATA_AUTO));
		compound.putInt("DataFIRE_MODE", this.entityData.get(DATA_FIRE_MODE));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataOPEN_TICK"))
			this.entityData.set(DATA_OPEN_TICK, compound.getInt("DataOPEN_TICK"));
		if (compound.contains("DataCLOSE_TICK"))
			this.entityData.set(DATA_CLOSE_TICK, compound.getInt("DataCLOSE_TICK"));
		if (compound.contains("DataWORKING"))
			this.entityData.set(DATA_WORKING, compound.getBoolean("DataWORKING"));
		if (compound.contains("DataFIRE_COOLDOWN"))
			this.entityData.set(DATA_FIRE_COOLDOWN, compound.getInt("DataFIRE_COOLDOWN"));
		if (compound.contains("DataCOMMON_AMMO"))
			this.entityData.set(DATA_COMMON_AMMO, compound.getInt("DataCOMMON_AMMO"));
		if (compound.contains("DataSUPER_AMMO"))
			this.entityData.set(DATA_SUPER_AMMO, compound.getInt("DataSUPER_AMMO"));
		if (compound.contains("DataTARGET_ANGLE"))
			this.entityData.set(DATA_TARGET_ANGLE, compound.getInt("DataTARGET_ANGLE"));
		if (compound.contains("DataCURRENT_ANGLE"))
			this.entityData.set(DATA_CURRENT_ANGLE, compound.getInt("DataCURRENT_ANGLE"));
		if (compound.contains("DataSTART_TICK"))
			this.entityData.set(DATA_START_TICK, compound.getInt("DataSTART_TICK"));
		if (compound.contains("DataAUTO"))
			this.entityData.set(DATA_AUTO, compound.getBoolean("DataAUTO"));
		if (compound.contains("DataFIRE_MODE"))
			this.entityData.set(DATA_FIRE_MODE, compound.getInt("DataFIRE_MODE"));
	}

	@Override
	public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
		ItemStack itemstack = sourceentity.getItemInHand(hand);
		InteractionResult retval = InteractionResult.sidedSuccess(this.level().isClientSide());
		super.mobInteract(sourceentity, hand);
		return InteractFireMortarProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this, sourceentity);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		AegirMortarTicksProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.entityData.set(
			DATA_CURRENT_ANGLE,
			angleTick(this.entityData.get(DATA_CURRENT_ANGLE), 
				this.entityData.get(DATA_TARGET_ANGLE)
			)
		);
		if(this.level() instanceof ServerLevel s){
			final Vec3 _center = this.position().add(0,2,0);
			List<ItemEntity> items = s.getEntitiesOfClass(
				ItemEntity.class, new AABB(_center, _center).inflate(2.25), e -> true
				);
			for(ItemEntity item: items) mergeGunpowder(item);
		}
		this.refreshDimensions();
	}

	private void mergeGunpowder(ItemEntity item){
        ItemStack stack = item.getItem();
        int count = stack.getCount();
        if (count >= 999) return;
        if(stack.is(Items.GUNPOWDER)){
            int curNum = getCommonAmmo();
            while(curNum < 999 && count > 0) {
                curNum++; count--;
            }
            setCommonAmmo(curNum);
        } else if (stack.is(CaerulaArborModItems.TRAIL_POWDER_CORE.get())){
        		int curNum = getSuperAmmo();
            while(curNum < 72 && count > 0) {
                curNum++; count--;
            }
            setSuperAmmo(curNum);
        } else return;
        stack.setCount(count);
        item.setItem(stack);
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.updateSwingTime();
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 500);
		builder = builder.add(Attributes.ARMOR, 20);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 15);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		//if (this.entityData.get(DATA_START_TICK) > 0){
		//	return event.setAndContinue(RawAnimation.begin().thenPlay("animation.aegir_mortar.start"));
		//} else if (this.entityData.get(DATA_OPEN_TICK) > 0){
		//	return event.setAndContinue(RawAnimation.begin().thenPlay("animation.aegir_mortar.sleep_to_idle"));
		//} else if (this.entityData.get(DATA_CLOSE_TICK) > 0){
		//	return event.setAndContinue(RawAnimation.begin().thenPlay("animation.aegir_mortar.idle_to_sleep"));
		//}
		if (this.animationprocedure.equals("empty")) {
			if (this.entityData.get(DATA_WORKING)){
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.aegir_mortar.idle"));
			} 
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.aegir_mortar.sleep"));
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
		if (this.deathTime == 5) {
			this.remove(AegirMortarEntity.RemovalReason.KILLED);
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

	public int getGunYRot(){
		return 360 - this.entityData.get(DATA_CURRENT_ANGLE);
	}

	public int angleTick(int currentAngle, int targetAngle){
		if (Math.abs(currentAngle - targetAngle) < 2) {
            return targetAngle;
        }
        int delta = targetAngle - currentAngle;
        
        if (delta > 180) {
            delta -= 360;
        } else if (delta < -180) {
            delta += 360;
        }

        if (delta > 0) {
            currentAngle += 1;
        } else {
            currentAngle -= 1;
        }

        return currentAngle;
	}

	private int getCommonAmmo(){return this.entityData.get(DATA_COMMON_AMMO);}
    private int getSuperAmmo(){return this.entityData.get(DATA_SUPER_AMMO);}
    private void setCommonAmmo(int value){this.entityData.set(DATA_COMMON_AMMO, Mth.clamp(value, 0, 999));}
    private void setSuperAmmo(int value){this.entityData.set(DATA_SUPER_AMMO, Mth.clamp(value, 0, 72));}

//	private boolean isWorking(){return this.entityData.get(DATA_WORKING);}
//
//    public static ItemStack getItemFromSlot(int slot, int count,Item item){
//        int filledSlot = count / 64 - 1;
//        int remainCount = count % 64;
//        if(slot <= filledSlot) return new ItemStack(item, 64);
//        else if (remainCount > 0) return new ItemStack(item, remainCount);
//        return ItemStack.EMPTY;
//    }
//
//    public int getContainerSize(){return 18;}
//    public boolean isEmpty(){return getCommonAmmo() + getSuperAmmo() <= 0;}
//
//    public @NotNull ItemStack getItem(int slot){
//        if(slot < 0 || slot >= getContainerSize()) return ItemStack.EMPTY;
//        return slot <= 15 ? getItemFromSlot(slot, getCommonAmmo(), Items.GUNPOWDER) : getItemFromSlot(slot - 16, getSuperAmmo(), CaerulaArborModItems.TRAIL_POWDER_CORE.get());
//    }
//    public @NotNull ItemStack removeItem(int slot, int amount){
//        if(slot < 0 || slot >= getContainerSize()) return ItemStack.EMPTY;
//        ItemStack toBeRemoved = getItem(slot);
//        int count = toBeRemoved.getCount();
//        amount  = Math.min(amount, count);
//        if(amount > 0){
//            if(slot <= 15) setCommonAmmo(getCommonAmmo() - amount);
//            else setSuperAmmo(getSuperAmmo() - amount);
//            toBeRemoved.setCount(amount);
//            return toBeRemoved;
//        }
//        return ItemStack.EMPTY;
//    }
//    public @NotNull ItemStack removeItemNoUpdate(int slot){
//        return removeItem(slot, 64);
//    }
//    public void setItem(int slot, @NotNull ItemStack itemstack){
//        int heCount = itemstack.getCount();
//        while (heCount > 0 && canPlaceItem(slot, itemstack)){
//            heCount --;
//            itemstack.setCount(heCount);
//            if(slot <= 15) setCommonAmmo(getCommonAmmo() + 1);
//            else setSuperAmmo(getSuperAmmo() + 1);
//        }
//    }
//    public void setChanged(){}
//    public boolean stillValid(@NotNull Player player){return true;}
//    private boolean isAcceptable(int slot, ItemStack itemstack){return slot <= 15 ? itemstack.is(Items.GUNPOWDER) : itemstack.is(CaerulaArborModItems.TRAIL_POWDER_CORE.get());}
//    public boolean canPlaceItem(int slot, @NotNull ItemStack itemstack) {
//        boolean unfilled = canTakeItem(this, slot, itemstack);
//        boolean whiteListed = isAcceptable(slot, itemstack);
//        return whiteListed && unfilled;
//    }
//    public boolean canTakeItem(@NotNull Container container, int slot, @NotNull ItemStack itemstack) {
//        if(!isAcceptable(slot, itemstack)) return false;
//        if(container instanceof AegirMortarEntity mortar && mortar.isWorking())
//            return slot <= 15 ? countItem(itemstack.getItem()) < 999 : countItem(itemstack.getItem()) < 72;
//        return false;
//    }
//    public int countItem(@NotNull Item item){
//        if(item == Items.GUNPOWDER) return getCommonAmmo();
//        else if (item == CaerulaArborModItems.TRAIL_POWDER_CORE.get()) return getSuperAmmo();
//        return 0;
//    }
//    public void clearContent(){
//        setSuperAmmo(0);
//        setCommonAmmo(0);
//    }
//    public boolean contains(ItemStack itemStack){
//    		return countItem(itemStack) > 0;
//    }
//    public boolean hasAnyOf(Set<Item> itemSet) {
//        return itemSet.contains(Items.GUNPOWDER) || itemSet.contains(CaerulaArborModItems.TRAIL_POWDER_CORE.get());
//    }
}
