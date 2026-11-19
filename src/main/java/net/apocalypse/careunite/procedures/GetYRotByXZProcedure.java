package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;

public class GetYRotByXZProcedure {
	public static double execute(Entity a, Entity b) {
		if (a == null || b == null)
			return 0;
		double dx = b.getX() - a.getX();
		double dz = b.getZ() - a.getZ();
		return (Mth.atan2(dz, dx) * (double)(180F / Math.PI)) - 90D;
	}
}
