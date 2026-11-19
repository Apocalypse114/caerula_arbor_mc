package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class GetPointFromHeightProcedure {
	public static double execute(Entity entity, double point) {
		if (entity == null)
			return 0;
		double yyy = 0;
		yyy = Math.min(128, Math.max(entity.getY(), -64));
		return point * (0.5 + (yyy + 64) * 0.0078125);
	}
}
