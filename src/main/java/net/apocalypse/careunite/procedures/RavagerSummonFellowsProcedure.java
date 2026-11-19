package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.world.entity.EntityType;

public class RavagerSummonFellowsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double t) {
		if (entity == null)
			return;
		if (world instanceof ServerLevel _level) {
			double dx = 0;
			double dz = 0;
			for (int index0 = 0; index0 < (int) t; index0++) {
				int fellow = Mth.nextInt(world.getRandom(), 0, 4);
				EntityType<?> type = null;
				if (fellow == 0) {
					type = CaerulaArborModEntities.OCEANIZED_PILLAGER.get();
				} else if (fellow == 1) {
					type = CaerulaArborModEntities.OCEANIZED_VINDICATOR.get();
				} else if (fellow == 2) {
					type = CaerulaArborModEntities.OCEANIZED_VILLAGER.get();
				} else if (fellow == 3) {
					type = CaerulaArborModEntities.OCEANIZED_WITCH.get();
				} else if (fellow == 4) {
					type = CaerulaArborModEntities.OCEANIZED_EVOKER.get();
				}
				if (type != null){
					Entity entityToSpawn = type.spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setDeltaMovement(0, 0.15, 0);
						EmrelySyncTeamProcedure.execute(entity, entityToSpawn);
					}
				}
			}
		}
	}
}
