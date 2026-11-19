package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class StartAllEchoProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity.isShiftKeyDown()) {
			for (int index0 = 0; index0 < 8; index0++) {
				SetEchoProcedure.execute(entity, -1, index0);
			}
		} else {
			for (int index1 = 0; index1 < 8; index1++) {
				SetEchoProcedure.execute(entity, 4800, index1);
			}
		}
	}
}
