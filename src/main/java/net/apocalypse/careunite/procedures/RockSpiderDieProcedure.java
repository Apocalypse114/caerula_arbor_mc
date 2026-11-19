package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class RockSpiderDieProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double dura = 0;
		for (int index0 = 0; index0 < 4; index0++) {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.TIDUTANT_EXCRESCENCE.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot((float) Mth.nextDouble(RandomSource.create(), 0, 360));
					entityToSpawn.setYBodyRot((float) Mth.nextDouble(RandomSource.create(), 0, 360));
					entityToSpawn.setYHeadRot((float) Mth.nextDouble(RandomSource.create(), 0, 360));
					entityToSpawn.setDeltaMovement((Mth.nextDouble(RandomSource.create(), -0.2, 0.2)), 0, (Mth.nextDouble(RandomSource.create(), -0.2, 0.2)));
				}
			}
		}
	}
}
