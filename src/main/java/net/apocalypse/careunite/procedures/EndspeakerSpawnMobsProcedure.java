package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModGameRules;

public class EndspeakerSpawnMobsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double elite_chan, double n) {
		if (entity == null)
			return;
		double tx = 0;
		double ty = 0;
		double tz = 0;
		Entity sss = null;
		if (!CaerulaArborModVariables.MapVariables.get(world).endspeakerSummon) {
			return;
		}
		if (GetSeabornNumProcedure.execute(world, x, y, z) >= (world.getLevelData().getGameRules().getInt(CaerulaArborModGameRules.CLONE_NUMBER_LIMIT))) {
			return;
		}
		for (int index0 = 0; index0 < 8; index0++) {
			tx = x + Mth.nextInt(RandomSource.create(), -8, 8);
			tz = z + Mth.nextInt(RandomSource.create(), -8, 8);
			ty = FindValidYForCatProcedure.execute(world, x, y, z, tx, y, tz);
			if (ty < 999) {
				sss = SelectGoldenageMobsProcedure.execute(world, tx, ty, tz);
				EmrelySyncTeamProcedure.execute(entity, sss);
				break;
			}
		}
		for (int index1 = 0; index1 < (int) (n - 1); index1++) {
			for (int index2 = 0; index2 < 8; index2++) {
				tx = x + Mth.nextInt(RandomSource.create(), -8, 8);
				tz = z + Mth.nextInt(RandomSource.create(), -8, 8);
				ty = FindValidYForCatProcedure.execute(world, x, y, z, tx, y, tz);
				if (ty < 999) {
					sss = SummonRandomSeabornProcedure.execute(world, elite_chan, tx, ty, tz);
					EmrelySyncTeamProcedure.execute(entity, sss);
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.CLOUD, tx, (ty + 0.75), tz, 64, 0.75, 0.75, 0.75, 0.1);
					break;
				}
			}
		}
	}
}
