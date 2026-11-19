package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;

import net.apocalypse.careunite.CaerulaArborMod;

public class RimedWitherShootProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		InteractionResult start = InteractionResult.PASS;
		new Object() {
			void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
				AngledWitherShootProcedure.execute(world, entity, entity.getYRot() + 30 * timedloopiterator);
				final int tick2 = ticks;
				CaerulaArborMod.queueServerWork(tick2, () -> {
					if (timedlooptotal > timedloopiterator + 1) {
						timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
					}
				});
			}
		}.timedLoop(0, 12, 1);
	}
}
