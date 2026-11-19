package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.CaerulaArborMod;

public class MortarDescendingSmokeProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double firemode) {
		if (entity == null)
			return;
		new Object() {
			void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, (y + 20 - timedloopiterator * 2), z, 2, 0.1, 0.1, 0.1, 0.05);
				final int tick2 = ticks;
				CaerulaArborMod.queueServerWork(tick2, () -> {
					if (timedlooptotal > timedloopiterator + 1) {
						timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
					}
				});
			}
		}.timedLoop(0, 10, 1);
		CaerulaArborMod.queueServerWork(10, () -> {
			if (firemode == 0) {
				MortarFireExplodeProcedure.execute(world, x, y, z, entity, 3, 1, firemode);
			} else if (firemode == 1) {
				MortarFireExplodeProcedure.execute(world, x, y, z, entity, 6, 2, firemode);
			} else if (firemode == 2) {
				MortarFireExplodeProcedure.execute(world, x, y, z, entity, 12, 2.5, firemode);
				CaerulaArborMod.queueServerWork(2, () -> {
					MortarFireExplodeProcedure.execute(world, x, y, z, entity, 12, 1.5, firemode);
				});
				CaerulaArborMod.queueServerWork(4, () -> {
					MortarFireExplodeProcedure.execute(world, x, y, z, entity, 12, 1.5, firemode);
				});
			}
		});
	}
}
