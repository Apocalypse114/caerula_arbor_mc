package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class CallMyHorseProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = CaerulaArborModEntities.LAST_KNIGHT_AND_HORSE.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(entity.getYRot());
				entityToSpawn.setYBodyRot(entity.getYRot());
				entityToSpawn.setYHeadRot(entity.getYRot());
				entityToSpawn.setXRot(entity.getXRot());
				SyncTeamAndTargetProcedure.execute(entity, entityToSpawn);
			}
		}
	}
}
