package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class SummonMyCatAtProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double t = 0;
		double angl = 0;
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = CaerulaArborModEntities.OCEANIZED_CAT.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(entity.getYRot());
				EmrelySyncTeamProcedure.execute(entity, entityToSpawn);
				entityToSpawn.setYBodyRot(entity.getYRot());
				entityToSpawn.setYHeadRot(entity.getYRot());
			}
		}
		
	}
}
