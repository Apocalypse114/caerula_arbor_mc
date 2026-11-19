package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class CenserRimedParticleProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, double r) {
		double d = 0;
		double angle = 0;
		d = r;
		for (int index0 = 0; index0 < 120; index0++) {
			angle = index0 * 3;
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.SPECTER_GLITTER.get()), (x + d * Math.sin(angle)), y, (z + d * Math.cos(angle)), 1, 0.1, 0.1, 0.1, 0.1);
		}
	}
}
