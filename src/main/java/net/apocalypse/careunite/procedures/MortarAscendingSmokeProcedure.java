package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.CaerulaArborMod;

public class MortarAscendingSmokeProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		new Object() {
			void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, (y + timedloopiterator * 2), z, 2, 0.1, 0.1, 0.1, 0.05);
				final int tick2 = ticks;
				CaerulaArborMod.queueServerWork(tick2, () -> {
					if (timedlooptotal > timedloopiterator + 1) {
						timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
					}
				});
			}
		}.timedLoop(0, 10, 1);
	}
}
