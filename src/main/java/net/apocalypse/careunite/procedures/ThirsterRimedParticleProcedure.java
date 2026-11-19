package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class ThirsterRimedParticleProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, double r) {
		double d = 0;
		double angle = 0;
		d = r;
		for (int index0 = 0; index0 < 120; index0++) {
			angle = index0 * 3;
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.NERVOUS_IMPAIRMENT.get()), (x + d * Math.sin(angle)), (y + 0.5), (z + d * Math.cos(angle)), 2, 0.1, 0.1, 0.1, 0.1);
		}
	}
}
