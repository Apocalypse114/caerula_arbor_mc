package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

public class MortarRimPtcProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, double r) {
		double d = 0;
		double angle = 0;
		d = r;
		for (int index0 = 0; index0 < 90; index0++) {
			angle = index0 * 4;
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.CLOUD, (x + d * Math.sin(angle)), (y + 0.25), (z + d * Math.cos(angle)), 1, 0.1, 0.1, 0.1, 0.1);
		}
		for (int index1 = 0; index1 < 120; index1++) {
			angle = index1 * 3;
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.FLAME, (x + d * Math.sin(angle)), (y + 0.25), (z + d * Math.cos(angle)), 1, 0.1, 0.1, 0.1, 0.1);
		}
	}
}
