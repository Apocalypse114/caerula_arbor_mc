package net.apocalypse.careunite.core;

import net.apocalypse.careunite.CaerulaArborMod;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;
import net.apocalypse.careunite.entity.OceanizedWardenisEntity;
import net.apocalypse.careunite.procedures.EmrelySyncTeamProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import java.util.List;
import java.util.Optional;
import net.minecraft.world.entity.MobSpawnType;
import net.apocalypse.careunite.entity.OceanizedWardenisEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

import net.apocalypse.careunite.CaerulaArborMod;
import net.minecraftforge.registries.ForgeRegistries;

import net.apocalypse.careunite.procedures.EmrelySyncTeamProcedure;

public class EntitySpawnHelper {

	public static boolean shouldLog(){
		return CaerulaConfigsConfiguration.DEBUG.get();
	}

    // 定义实体标签的 TagKey（替换 your_mod_id 和 your_tag_name）
    private static final TagKey<EntityType<?>> ENTITY_TAG = TagKey.create(
            Registries.ENTITY_TYPE,
            new ResourceLocation("caerula_arbor_reunite", "izumik_discovers")
    );
    private static final TagKey<EntityType<?>> CAERULA_TAG = TagKey.create(
            Registries.ENTITY_TYPE,
            new ResourceLocation("caerula_arbor_reunite", "caerula_discovers")
    );
    
    public static boolean spawnRandomEntityFromTag(Entity me, ServerLevel serverLevel, double x, double y, double z){
    	if(serverLevel == null) return false;
		return spawnEntity(me, serverLevel, x, y, z);
    }
    public static boolean spawnCaerulaEntityFromTag(Entity me, ServerLevel serverLevel, double x, double y, double z){
    	if(serverLevel == null) return false;
		return spawnCaerulaEntity(me, serverLevel, x, y, z);
    }

    public static Optional<EntityType<?>> randomEntityTypeInTag(Level level, TagKey<EntityType<?>> tag) {
        Registry<EntityType<?>> registry = level.registryAccess().registryOrThrow(Registries.ENTITY_TYPE);
        Optional<HolderSet.Named<EntityType<?>>> optional = registry.getTag(tag);
        if (optional.isEmpty()) return Optional.empty();
        List<Holder<EntityType<?>>> entitiesInTag = optional.get().stream().toList();
        if (entitiesInTag.isEmpty()) return Optional.empty();
        RandomSource random = level.getRandom();
        EntityType<?> selected = entitiesInTag.get(random.nextInt(entitiesInTag.size())).value();
        return Optional.of(selected);
    }
    private static boolean spawnEntity(Entity me,  ServerLevel level, double x, double y,double z) {
    	Optional<EntityType<?>> optionalEntityType = randomEntityTypeInTag(level, ENTITY_TAG);
    	if (optionalEntityType.isEmpty()) return false;
    	EntityType<?> entityType = optionalEntityType.get();
    	if(entityType == null) return false;
        Entity entity = entityType.spawn(level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
        EmrelySyncTeamProcedure.execute(me, entity);
        if (entity instanceof OceanizedWardenisEntity www) {
			 AttributeInstance mh = www.getAttribute(Attributes.MAX_HEALTH);
			 if(mh != null) mh.setBaseValue(mh.getBaseValue() * 0.35);
			 www.setHealth((float)(www.getMaxHealth()));
        }
        if(shouldLog()) CaerulaArborMod.LOGGER.info("Izumik offspring at {}, {}, {} changes into {}", x, y, z, regname(entityType));
        return true;
    }
    private static boolean spawnCaerulaEntity(Entity me, ServerLevel level, double x, double y,double z) {
    	Optional<EntityType<?>> optionalEntityType = randomEntityTypeInTag(level, CAERULA_TAG);
    	if (optionalEntityType.isEmpty()) return false;
    	EntityType<?> entityType = optionalEntityType.get();
    	if(entityType == null) return false;
        Entity entity = entityType.spawn(level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
        if (entity == null) return false;
        EmrelySyncTeamProcedure.execute(me, entity);
        if(shouldLog())
        	CaerulaArborMod.LOGGER.info("Caerula offspring at {}, {}, {} changes into {}", x, y, z, regname(entityType));
        return true;
    }

    public static String regname(EntityType type){
    	return ForgeRegistries.ENTITY_TYPES.getKey(type).toString();
    }
}