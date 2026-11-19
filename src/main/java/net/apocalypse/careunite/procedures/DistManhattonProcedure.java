package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class DistManhattonProcedure {
	public static double execute(Entity a, Entity b) {
		if (a == null || b == null)
			return 0;
		return Math.abs(a.getX() - b.getX()) + Math.abs(a.getZ() - b.getZ());
	}
}
