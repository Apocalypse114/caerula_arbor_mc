package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class MartusParticleRim1Procedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double r = 0;
		double t = 0;
		double tx = 0;
		double yz = 0;
		double randint = 0;
		randint = Mth.nextInt(RandomSource.create(), 0, 39);
		for (int index0 = 0; index0 < 40; index0++) {
			t = randint + index0 * 9;
			r = 1.5 + 0.5 * Math.sin(Math.toRadians(index0 * 36));
			tx = x + r * Math.sin(Math.toRadians(t));
			yz = z + r * Math.cos(Math.toRadians(t));
			world.addParticle((SimpleParticleType) (CaerulaArborModParticleTypes.MARTUS_CHARS.get()), tx, (y + 1), yz, 0, 0.15, 0);
			world.addParticle((SimpleParticleType) (CaerulaArborModParticleTypes.MARTUS_CHARS.get()), tx, (y + 0.8), yz, 0, (-0.08), 0);
		}
	}
}
