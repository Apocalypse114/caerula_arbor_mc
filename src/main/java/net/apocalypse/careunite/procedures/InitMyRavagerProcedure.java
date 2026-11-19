package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

public class InitMyRavagerProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = CaerulaArborModEntities.OCEANIZED_RAVAGER.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				if (entityToSpawn instanceof LivingEntity living){
					AttributeInstance instance = living.getAttribute(Attributes.MAX_HEALTH);
	            	if (instance != null) {
	                	instance.setBaseValue(instance.getBaseValue() * 1.5);
	            	}
	            	living.setHealth(living.getMaxHealth());
				}
				entity.startRiding(entityToSpawn);
			}
		}
	}
}
