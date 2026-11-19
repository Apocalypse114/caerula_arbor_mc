
package net.apocalypse.careunite.entity;

import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;

import net.apocalypse.careunite.init.CaerulaArborModEntities;

import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;
import net.minecraft.nbt.CompoundTag;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.apocalypse.careunite.procedures.IsValidEnemyForAlchemyProcedure;
import net.minecraft.server.level.ServerLevel;
import java.util.List;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import java.util.UUID;
import software.bernie.geckolib.core.animation.AnimationController;
import net.apocalypse.careunite.procedures.DealZhixuBurnProcedure;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class InquisitionFireEntity extends Entity implements GeoEntity {
	public InquisitionFireEntity(PlayMessages.SpawnEntity packet, Level world) {
		super(CaerulaArborModEntities.INQUISITION_FIRE.get(), world);
	}
	public InquisitionFireEntity(EntityType<? extends InquisitionFireEntity> type, Level world) {
		super(type, world);
	}

	public UUID ownerUUID = null;
	public Entity owner = null;

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	public static final EntityDataAccessor<Integer> AGE = SynchedEntityData.defineId(InquisitionFireEntity.class, EntityDataSerializers.INT);

	public int getAge(){
		return entityData.get(AGE);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public void setAge(int s){
		entityData.set(AGE, s);
	}

	public void setOwner(Entity owner){
		this.ownerUUID = owner.getUUID();
	}

	public Entity getOwner(){
		if (owner == null && this.level() instanceof ServerLevel sss && ownerUUID != null) owner = sss.getEntity(ownerUUID);
		return owner;
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar data) {
		data.add(new AnimationController<>(this, "idle", 0, this::movementPredicate));
	}

	private PlayState movementPredicate(AnimationState event) {
		if (getAge() <= 3) return event.setAndContinue(RawAnimation.begin().thenLoop("lifecycle_3s"));
		if (getAge() <= 5) return event.setAndContinue(RawAnimation.begin().thenLoop("lifecycle_3s"));
		return event.setAndContinue(RawAnimation.begin().thenLoop("lifecycle_10s"));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public void tick() {
		super.tick();
		if (this.tickCount % 20 == 10){
			Entity owner = getOwner();
			List<LivingEntity> _entfound = this.level().getEntitiesOfClass(LivingEntity.class, 
				new AABB(this.position().add(-2.5, -1, -2.5), this.position().add(2.5, 2, 2.5)), 
				e -> !this.is(e) && (owner == null ? e instanceof Monster: IsValidEnemyForAlchemyProcedure.execute(e, owner)));
			BlockPos myPos = this.blockPosition();
			for (LivingEntity entityiterator : _entfound) {
				BlockPos hisPos = entityiterator.blockPosition();
				if (hisPos.distManhattan(myPos) <= 2) {
					entityiterator.invulnerableTime = 0;
					if (entityiterator.hurt(this.level().damageSources().magic(), 4)){
						entityiterator.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.MUTE.get(), 80, 0, false, false));
					}
					DealZhixuBurnProcedure.execute(entityiterator, 50);
				}
			}
		}
		if (this.tickCount >= getAge() * 20) this.discard();
	}

	public void defineSynchedData(){
		entityData.define(AGE, 5);
    }

    public void readAdditionalSaveData(CompoundTag tag){
        setAge(tag.getInt("age"));
        if (tag.contains("owner"))
			ownerUUID = tag.getUUID("owner");
    }

    public void addAdditionalSaveData(CompoundTag tag){
        tag.putInt("age", getAge());
        if (ownerUUID != null) tag.putUUID("owner", ownerUUID);
    }
}
