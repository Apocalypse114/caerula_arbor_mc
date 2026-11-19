package net.apocalypse.careunite.core;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.world.entity.Pose;

public interface Seaborn {
	enum Type{
        PRIMITIVE(true, true, true),
        CONVERTED(true, true, false),
        PET(false, false, false),
        MISC(false, false, false);
        private final boolean triggerMigration;
        private final boolean receiveMigration;
        private final boolean shouldReproduce;
        Type(boolean triggerMigration, boolean receiveMigration, boolean reproduce){
            this.shouldReproduce = reproduce;
            this.triggerMigration = triggerMigration;
            this.receiveMigration = receiveMigration;
        }
        boolean triggerMigration(){return this.triggerMigration;}
        boolean receiveMigration(){return this.receiveMigration;}
        boolean shouldReproduce(){return this.shouldReproduce;}
    }
    
    enum Tier{
        TINY(0), COMMON(1), ELITE(2), BOSS(3), DIVINITY(4);
        private final int level;
        Tier(int l){this.level = l;}
        public int level() {return level;}
    }

    default Type getSeabornType() {
    	return Type.PRIMITIVE;
    }
    default Tier getTier(){
        return Tier.COMMON;
    }
    default boolean isElite(){
        return this.getTier() == Tier.ELITE;
    }
    default boolean isBoss(){
        return this.getTier().level() >= 3;
    }
    default boolean isTiny(){
        return this.getTier() == Tier.TINY;
    }
    default boolean isConverted(){
        return this.getSeabornType() == Type.CONVERTED;
    }
    default boolean isPet(){
        return this.getSeabornType() == Type.PET;
    }

    String ORIGINAL = "caerula_arbor_reunite_conversion_original";

    @Nullable
    default Entity getOriginalEntity(){
        if (this.isConverted()) {
            if (this instanceof LivingEntity living) {
                CompoundTag tag = living.getPersistentData().getCompound(ORIGINAL);
                Optional<Entity> entity = EntityType.create(tag, living.level());
                return entity.orElse(null);
            }
        }
        return null;
    }

    default boolean setOriginalEntity(LivingEntity original){
        if (this.isConverted()) {
            CompoundTag tag = new CompoundTag();
            if (this instanceof LivingEntity living && original.save(tag)) {
                living.getPersistentData().put(ORIGINAL, tag);
                return true;
            }
        }
        return false;
    }

    static boolean revertToOriginal(Seaborn seaborn, Consumer<Entity> postRevert){
        if (seaborn instanceof LivingEntity living && living.level() instanceof ServerLevel serverLevel) {
            Entity entity = seaborn.getOriginalEntity();
            if(entity instanceof LivingEntity ll) {
            	ll.setPose(Pose.STANDING);
            	entity.setPos(living.position());
                serverLevel.addFreshEntity(entity);
                postRevert.accept(entity);
                return true;
            }
        }
        return false;
    }
    static boolean revertToOriginal(Seaborn seaborn){
        return revertToOriginal(seaborn, e -> {});
    }
}
