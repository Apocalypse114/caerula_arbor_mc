package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;

import net.apocalypse.careunite.CaerulaArborMod;

public class RoundWitheriaShootProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		InteractionResult start = InteractionResult.PASS;
		new Object() {
			void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
				AngledWitheriaShootProcedure.execute(world, entity, entity.getYRot() + 12 * timedloopiterator);
				AngledWitheriaShootProcedure.execute(world, entity, entity.getYRot() + 24 * timedloopiterator);
				AngledWitheriaShootProcedure.execute(world, entity, entity.getYRot() + 36 * timedloopiterator);
				AngledWitheriaShootProcedure.execute(world, entity, entity.getYRot() + 48 * timedloopiterator);
				final int tick2 = ticks;
				CaerulaArborMod.queueServerWork(tick2, () -> {
					if (timedlooptotal > timedloopiterator + 1) {
						timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
					}
				});
			}
		}.timedLoop(0, 15, 1);
	}
}
