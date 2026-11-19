package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class DistributeIllusionProcedure {

	public static void summonSyncedIllusion(ServerLevel world, double x, double y, double z, Entity entity){
		Entity entityToSpawn = CaerulaArborModEntities.OCEAN_ILLUSION.get().spawn(world, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
		if (entityToSpawn != null) {
			entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
			EmrelySyncTeamProcedure.execute(entity, entityToSpawn);
		}
	}
	
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (world instanceof ServerLevel _level) {
			for (int index0 = 0; index0 < 5; index0++) {
				if (IsValidForManProcedure.execute(world, x + 4 - index0, y, z)) {
					summonSyncedIllusion(_level, x + 4 - index0, y, z, entity);
					break;
				}
			}
			for (int index1 = 0; index1 < 5; index1++) {
				if (IsValidForManProcedure.execute(world, x - (4 - index1), y, z)) {
					summonSyncedIllusion(_level,x - (4 - index1), y, z, entity);
					break;
				}
			}
			for (int index2 = 0; index2 < 5; index2++) {
				if (IsValidForManProcedure.execute(world, x, y, z + 4 - index2)) {
					summonSyncedIllusion(_level, x, y, z + 4 - index2, entity);
					break;
				}
			}
			for (int index3 = 0; index3 < 5; index3++) {
				if (IsValidForManProcedure.execute(world, x, y, z - (4 - index3))) {
					summonSyncedIllusion(_level, x, y, z - (4 - index3), entity);
					break;
				}
			}
		}
	}
}
