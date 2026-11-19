package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class RunvolutionThrowWaveProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		for (int index0 = 0; index0 < 8; index0++) {
			if (!world.isEmptyBlock(BlockPos.containing(x, y - index0 - 1, z))) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = CaerulaArborModEntities.RUNVOLUTION_WAVE.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
				}
				return;
			}
		}
	}
}
