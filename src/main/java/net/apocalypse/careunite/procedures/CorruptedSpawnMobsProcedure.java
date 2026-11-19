package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.init.CaerulaArborModGameRules;

public class CorruptedSpawnMobsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double n) {
		if (entity == null)
			return;
		double tx = 0;
		double ty = 0;
		double tz = 0;
		double R = 0;
		double T = 0;
		Entity sss = null;
		if (GetSeabornNumProcedure.execute(world, x, y, z) >= (world.getLevelData().getGameRules().getInt(CaerulaArborModGameRules.CLONE_NUMBER_LIMIT))) {
			return;
		}
		for (int index0 = 0; index0 < (int) n; index0++) {
			for (int index1 = 0; index1 < 8; index1++) {
				R = Mth.nextInt(RandomSource.create(), 4, 16);
				T = Mth.nextDouble(RandomSource.create(), 0, 6.283);
				tx = x + R * Math.sin(T);
				tz = z + R * Math.cos(T);
				ty = FindValidYForCatProcedure.execute(world, x, y, z, tx, y, tz);
				if (ty < 999) {
					sss = SummonRandomSeabornProcedure.execute(world, 0.33, tx, ty, tz);
					EmrelySyncTeamProcedure.execute(entity, sss);
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.CLOUD, tx, (ty + 0.75), tz, 64, 0.75, 0.75, 0.75, 0.1);
					break;
				}
			}
		}
	}
}
