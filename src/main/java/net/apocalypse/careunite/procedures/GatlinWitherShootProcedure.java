package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.CaerulaArborMod;

public class GatlinWitherShootProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double vx = 0;
		double vy = 0;
		double vz = 0;
		new Object() {
			void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
				DownPourWitherProcedure.execute(world, x, y, z, entity);
				final int tick2 = ticks;
				CaerulaArborMod.queueServerWork(tick2, () -> {
					if (timedlooptotal > timedloopiterator + 1) {
						timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
					}
				});
			}
		}.timedLoop(0, 20, 1);
	}
}
