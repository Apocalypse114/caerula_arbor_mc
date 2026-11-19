package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.CaerulaArborMod;

public class IsharmlaToHstyleProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		new Object() {
			void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
				for (int index0 = 0; index0 < 120; index0++) {
					if (world instanceof ServerLevel _level)
						_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.EDERMAN_PTC.get()), (x + (10 - timedloopiterator) * Math.sin(Math.toRadians(3 * index0))), (y + 0.125),
								(z + (10 - timedloopiterator) * Math.cos(Math.toRadians(3 * index0))), 2, 0.05, 0.05, 0.05, 0.1);
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.END_ROD, (x + (10 - timedloopiterator) * Math.sin(Math.toRadians(3 * index0))), (y + 0.125), (z + (10 - timedloopiterator) * Math.cos(Math.toRadians(3 * index0))), 1, 0.05, 0.05, 0.05, 0.1);
					if (world instanceof ServerLevel _level)
						_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.EDERMAN_PTC.get()), (x + 2 * Math.sin(Math.toRadians(3 * index0))), (y + (10 - timedloopiterator) * 0.5), (z + 2 * Math.cos(Math.toRadians(3 * index0))),
								2, 0.05, 0.05, 0.05, 0.1);
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.END_ROD, (x + 2 * Math.sin(Math.toRadians(3 * index0))), (y + (10 - timedloopiterator) * 0.5), (z + 2 * Math.cos(Math.toRadians(3 * index0))), 1, 0.05, 0.05, 0.05, 0.1);
				}
				final int tick2 = ticks;
				CaerulaArborMod.queueServerWork(tick2, () -> {
					if (timedlooptotal > timedloopiterator + 1) {
						timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
					}
				});
			}
		}.timedLoop(0, 10, 2);
	}
}
