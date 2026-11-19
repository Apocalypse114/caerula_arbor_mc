
package net.apocalypse.careunite.entity;

import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.OwnableEntity;
import net.apocalypse.careunite.procedures.BeaconExplodeProcedure;

import net.minecraft.nbt.*;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;
import net.minecraft.sounds.SoundSource;
import net.apocalypse.careunite.init.CaerulaArborModSounds;

public class BeaconSignEntity extends Entity implements OwnableEntity{
	public static final EntityDataAccessor<Integer> TIME = SynchedEntityData.defineId(BeaconSignEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> TIME_O = SynchedEntityData.defineId(BeaconSignEntity.class, EntityDataSerializers.INT);
    private UUID ownerUUID = null;
    private LivingEntity owner = null;
    private UUID targetUUID = null;
    private LivingEntity target = null;

	public BeaconSignEntity(PlayMessages.SpawnEntity packet, Level world) {
		super(CaerulaArborModEntities.BEACON_SIGN.get(), world);
	}

	public BeaconSignEntity(EntityType<? extends BeaconSignEntity> type, Level world) {
		super(type, world);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public void setOwner(LivingEntity owner){
		if (owner == null) return;
        this.ownerUUID = owner.getUUID();
        this.owner = owner;
    }

    @Nullable
    public UUID getOwnerUUID(){
        return ownerUUID;
    }
    @Nullable
    public LivingEntity getOwner() {
        UUID uuid = this.getOwnerUUID();
        if (uuid == null) return null;
        if (owner == null && this.level() instanceof ServerLevel serverLevel
                && serverLevel.getEntity(uuid) instanceof LivingEntity living){
            this.owner = living;
        }
        return owner;
    }
    public void setAttaching(LivingEntity owner){
        this.targetUUID = owner.getUUID();
        this.target = owner;
    }
    @Nullable
    public LivingEntity getAttaching() {
        UUID uuid = this.targetUUID;
        if (uuid == null) return null;
        if (target == null && this.level() instanceof ServerLevel serverLevel
                && serverLevel.getEntity(uuid) instanceof LivingEntity living){
            this.target = living;
        }
        return target;
    }

    public void setT(int t){
    	entityData.set(TIME, t);
    }

    public void defineSynchedData(){
        entityData.define(TIME, 0);
        entityData.define(TIME_O, 0);
    }

    public void readAdditionalSaveData(CompoundTag tag){
        if (tag.contains("owner")) this.ownerUUID = tag.getUUID("owner");
        if (tag.contains("target")) this.targetUUID = tag.getUUID("target");
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag){
        tag.putUUID("target", targetUUID);
        tag.putUUID("owner", ownerUUID);
    }

    public float lerpT(float partialTick){
        return Mth.lerp(partialTick, entityData.get(TIME_O), entityData.get(TIME));
    }

    public double height(float partialTick){
        float t = Math.max(lerpT(partialTick) - 5, 0);
        return 32 - Math.min(t * 0.4, 32);
    }

    public double texiaoH(float pt){
    	float t = lerpT(pt) - 85;
    	if (t > 0){
    		if (t > 5) t  = 10 - t;
    		return t * t * 0.12;
    	}
    	return 0;
    }
    public double texiaoW(float pt){
    	float t = lerpT(pt) - 85;
    	if (t > 0){
    		if (t > 5){
    			t -= 5;
    			return 5 + t * t * 0.2;
    		}
    		return 4 + 0.2 * t;
    	}
    	return 0;
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = this.getAttaching();
        if (target != null) this.setPos(target.position());
        int t = entityData.get(TIME);
        if (t == 85){
            BeaconExplodeProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), getOwner(), this);
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), CaerulaArborModSounds.BEACON_SKILL_EXP.get(), SoundSource.NEUTRAL, 
        		1, this.level().getRandom().nextFloat() * 0.4f + 0.8f);
        }
        if (t >= 95){
        	this.discard();
        	return;
        }

        entityData.set(TIME_O, t);
        entityData.set(TIME, t + 1);
    }
}
