
package net.apocalypse.careunite.entity;

import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraft.nbt.CompoundTag;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class TypheousSignEntity extends Entity {
	//public static final ItemStack PROJECTILE_ITEM = new ItemStack(CaerulaArborModBlocks.OCEAN_GLASSPANE.get());

	public TypheousSignEntity(PlayMessages.SpawnEntity packet, Level world) {
		super(CaerulaArborModEntities.TYPHEOUS_SIGN.get(), world);
	}

	public Entity target = null;

	public TypheousSignEntity(EntityType<? extends TypheousSignEntity> type, Level world) {
		super(type, world);
	}

	public TypheousSignEntity(EntityType<? extends TypheousSignEntity> type, double x, double y, double z, Level world) {
		super(type, world);
	}

	public TypheousSignEntity(EntityType<? extends TypheousSignEntity> type, LivingEntity entity, Level world) {
		super(type, world);
	}
	
public void defineSynchedData(){
    }

    public void readAdditionalSaveData(CompoundTag tag){
    }

    public void addAdditionalSaveData(CompoundTag tag){
    }

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.tickCount > 32) this.discard();
		if (target != null){
			Vec3 myPos = this.position();
			Vec3 hisPos = target.position();
			Vec3 lerpedPos = myPos.lerp(hisPos, 0.25);
			this.teleportTo(lerpedPos.x, lerpedPos.y, lerpedPos.z);
		}
	}

	public void withTarget(Entity t){
		this.target = t;
	}
}
