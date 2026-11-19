package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class StopAllEchoProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		for (int index0 = 0; index0 < 8; index0++) {
			SetEchoProcedure.execute(entity, 0, index0);
		}
	}
}
