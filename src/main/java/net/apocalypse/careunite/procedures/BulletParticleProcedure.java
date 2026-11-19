package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class BulletParticleProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		world.addParticle((SimpleParticleType) (CaerulaArborModParticleTypes.SEA_SPLASH.get()), x, y, z, 0, 0, 0);
	}
}
