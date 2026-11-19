
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
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.AABB;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.OwnableEntity;

import net.apocalypse.careunite.procedures.IsValidEnemyForAlchemyProcedure;
import net.apocalypse.careunite.init.CaerulaArborModSounds;
import net.minecraft.sounds.SoundSource;

public class BeaconSelectorEntity extends Entity implements OwnableEntity{
	public List<UUID> selectedUUIDs = new ArrayList<>();
    private static final String TAG_UUIDS = "selectedUUIDs";
    public static final EntityDataAccessor<Integer> TIME = SynchedEntityData.defineId(BeaconSelectorEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> TIME_O = SynchedEntityData.defineId(BeaconSelectorEntity.class, EntityDataSerializers.INT);
    private UUID ownerUUID = null;
    private LivingEntity owner = null;

	public BeaconSelectorEntity(PlayMessages.SpawnEntity packet, Level world) {
		super(CaerulaArborModEntities.BEACON_SELECTOR.get(), world);
	}

	public BeaconSelectorEntity(EntityType<? extends BeaconSelectorEntity> type, Level world) {
		super(type, world);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public void setOwner(LivingEntity owner){
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

    public void defineSynchedData(){
        entityData.define(TIME, 0);
        entityData.define(TIME_O, 0);
    }

    public void readAdditionalSaveData(CompoundTag tag){
        ListTag listTag = tag.getList(TAG_UUIDS, Tag.TAG_INT_ARRAY);
        if (listTag != null && !listTag.isEmpty()){
	        for (Tag uidtag: listTag){
	            try {
	                selectedUUIDs.add(NbtUtils.loadUUID(uidtag));
	            } catch (Exception ignored){}
	        }
        }
        if (tag.contains("owner")) this.ownerUUID = tag.getUUID("owner");
        entityData.set(TIME, tag.getInt("time"));
        entityData.set(TIME_O, tag.getInt("time_o"));
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag){
        ListTag listTag = new ListTag();
        for (UUID uid: selectedUUIDs){
            listTag.add(NbtUtils.createUUID(uid));
        }
        tag.put(TAG_UUIDS, listTag);
        tag.putUUID("owner", ownerUUID);
        tag.putInt("time", entityData.get(TIME));
        tag.putInt("time_o", entityData.get(TIME_O));
    }

    public float lerpT(float partialTick){
        return Mth.lerp(partialTick, entityData.get(TIME_O), entityData.get(TIME));
    }

    public double getDx(){
        return Math.min(entityData.get(TIME) * 0.8 - 32, 32);
    }
    public double lerpDx(float partialTick){
        return Math.min(lerpT(partialTick) * 0.8 - 32, 32);
    }
    public double getLen(float partialTick){
        float t = lerpT(partialTick);
        if (t <= 10) return t * 0.8;
        if (t > 75) return (85 - t) * 0.8;
        return 8;
    }

    @Override
    public @NotNull Vec3 getDeltaMovement() {
        return Vec3.ZERO;
    }

    @Override
    public void tick() {
        this.setDeltaMovement(Vec3.ZERO);
        super.tick();
        int t = entityData.get(TIME);

        if (t > 0 && t <= 85 && t % 5 == 0 && this.level() instanceof ServerLevel serverLevel){
        	LivingEntity ow = getOwner();
        	if (ow != null){
	            AABB aabb = new AABB(this.position().add(getDx(), 8, 32),
	                    this.position().add(getDx() + 4, -3, -32));
	            List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, aabb, 
	                    e -> !e.isDeadOrDying() && !selectedUUIDs.contains(e.getUUID()) 
	                    && IsValidEnemyForAlchemyProcedure.execute(ow, e));
	            for (LivingEntity living: list){
	                selectedUUIDs.add(living.getUUID());
	                putSignOn(living);
	            }
        	}
        }
        if (t >= 85){
            this.discard();
            return;
        }

        entityData.set(TIME_O, t);
        entityData.set(TIME, t + 1);
    }
    
    public void putSignOn(LivingEntity living){
        if (this.level() instanceof ServerLevel _level) {
        	_level.playSound(null, living.getX(), living.getY(), living.getZ(), CaerulaArborModSounds.BEACON_SKILL_SELECT.get(), SoundSource.NEUTRAL, 
        		1, _level.getRandom().nextFloat() * 0.4f + 0.8f);
			BeaconSignEntity entityToSpawn = CaerulaArborModEntities.BEACON_SIGN.get().create(_level);
			if (entityToSpawn != null) {
				entityToSpawn.setPos(living.position());
				entityToSpawn.setOwner(this.getOwner());
				entityToSpawn.setAttaching(living);
				entityToSpawn.setT(this.entityData.get(TIME));
				_level.addFreshEntity(entityToSpawn);
			}
		}
    }
}
