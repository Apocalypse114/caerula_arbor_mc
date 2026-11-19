package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class IsharmlaPtcStreamProcedure {
	public static void execute(LevelAccessor world, Entity sourceentity) {
		if (sourceentity == null)
			return;
		double sX = 0;
		double sY = 0;
		double sZ = 0;
		sX = sourceentity.getX();
		sY = sourceentity.getY();
		sZ = sourceentity.getZ();
		for (int index0 = 0; index0 < 12; index0++) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.MOIST_BOOM.get()), sX, (sY + 10 + index0), sZ, 6, (index0 * 0.1), (index0 * 0.1), (index0 * 0.1), 0);
		}
	}
}
