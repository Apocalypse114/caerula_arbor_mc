package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

public class PrayerRimProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double angle = 0;
		double d = 0;
		d = 5;
		for (int index0 = 0; index0 < 6; index0++) {
			angle = Mth.nextDouble(RandomSource.create(), 0, 6.283);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.ELECTRIC_SPARK, (x + d * Math.sin(angle)), (y + 0.4), (z + d * Math.cos(angle)), 2, 0.1, 0.1, 0.1, 0.1);
		}
	}
}
