package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class SpecterDiamondParticleProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, double t) {
		double R = 0;
		double dx = 0;
		double dz = 0;
		double tt = 0;
		R = t * 0.9;
		for (int index0 = 0; index0 < (int) (R + 1); index0++) {
			dx = index0 * 0.5;
			dz = (R - index0) * 0.5;
			world.addParticle((SimpleParticleType) (CaerulaArborModParticleTypes.SPECTER_GLITTER.get()), (x + dx), (y + 0.1), (z + dz), 0, 0.1, 0);
			world.addParticle((SimpleParticleType) (CaerulaArborModParticleTypes.SPECTER_GLITTER.get()), (x - dx), (y + 0.1), (z + dz), 0, 0.1, 0);
			world.addParticle((SimpleParticleType) (CaerulaArborModParticleTypes.SPECTER_GLITTER.get()), (x + dx), (y + 0.1), (z - dz), 0, 0.1, 0);
			world.addParticle((SimpleParticleType) (CaerulaArborModParticleTypes.SPECTER_GLITTER.get()), (x - dx), (y + 0.1), (z - dz), 0, 0.1, 0);
		}
		if (Math.random() < 0.15) {
			R = Mth.nextDouble(RandomSource.create(), 1, 8);
			tt = Mth.nextDouble(RandomSource.create(), 0, 6.283);
			world.addParticle((SimpleParticleType) (CaerulaArborModParticleTypes.SPECTER_CHARS.get()), (x + R * Math.cos(tt)), (y + 0.2), (z + R * Math.sin(tt)), 0, 0.25, 0);
		}
	}
}
