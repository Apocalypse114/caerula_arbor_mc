package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class DistributeIsharmlaTearProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double r = 0;
		double d = 0;
		double tx = 0;
		double tz = 0;
		double ty = 0;
		for (int index0 = 0; index0 < 8; index0++) {
			r = Mth.nextDouble(RandomSource.create(), 0, 6.283);
			d = Mth.nextDouble(RandomSource.create(), 4, 18);
			tx = x + d * Math.cos(r);
			tz = z + d * Math.sin(r);
			ty = FindYzforTearProcedure.execute(world, tx, y, tz);
			if (ty < 114110) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = CaerulaArborModEntities.ISHARMLA_TEAR.get().spawn(_level, BlockPos.containing(tx, ty, tz), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
				break;
			}
		}
	}
}
