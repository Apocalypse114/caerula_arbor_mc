package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.init.CaerulaArborModGameRules;

public class MartusTimedSpawnProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity eee = null;
		if (GetSeabornAroundProcedure.execute(world, x, y, z, entity) < (world.getLevelData().getGameRules().getInt(CaerulaArborModGameRules.CLONE_NUMBER_LIMIT))) {
			for (int index0 = 0; index0 < 2; index0++) {
				eee = SummonRandomSeabornProcedure.execute(world, 0.33, x, y, z);
				EmrelySyncTeamProcedure.execute(entity, eee);
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.CLOUD, x, y, z, 18, 0.6, 0.6, 0.6, 0.16);
			}
		}
	}
}
