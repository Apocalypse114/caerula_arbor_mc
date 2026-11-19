package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class ReduceEcoTimeProcedure {
	public static void execute(Entity entity, double id) {
		if (entity == null)
			return;
		double g = 0;
		g = GetEchoProcedure.execute(entity, id);
		if (g > 0) {
			SetEchoProcedure.execute(entity, Math.max(0, g - 1), id);
		}
	}
}
