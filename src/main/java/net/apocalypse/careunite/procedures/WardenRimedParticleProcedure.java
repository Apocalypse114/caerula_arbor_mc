package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

public class WardenRimedParticleProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double t = 0;
		double p = 0;
		double d = 0;
		t = Mth.nextDouble(RandomSource.create(), 0, 6.283);
		p = Mth.nextDouble(RandomSource.create(), 0, 6.283);
		d = 4;
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.SONIC_BOOM, (x + d * Math.cos(t) * Math.cos(p)), (y + d * Math.sin(p)), (z + d * Math.sin(t) * Math.cos(p)), 2, 0, 0, 0, 0.1);
	}
}
