package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.world.entity.LivingEntity;

public class SummonHurtSkadiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof ServerLevel _level) {
			LivingEntity entityToSpawn = CaerulaArborModEntities.SKADI.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setHealth(entityToSpawn.getMaxHealth() * 0.4f);
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
			}
		}
	}
}
