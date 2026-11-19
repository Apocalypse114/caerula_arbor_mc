package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

public class RingFangsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double D) {
		if (entity == null)
			return;
		double dt = 0;
		double r = 0;
		double fy = 0;
		double tx = 0;
		double tz = 0;
		dt = 360 / D;
		r = 0.5 * D;
		for (int index0 = 0; index0 < (int) D; index0++) {
			tx = x + r * Math.sin(Math.toRadians(dt * index0));
			tz = z + r * Math.cos(Math.toRadians(dt * index0));
			fy = FindGroundProcedure.execute(world, tx, y, tz);
			if (fy <= 114513) {
				SummonFangWithOwnerProcedure.execute(world, tx, fy, tz, entity);
			}
		}
	}
}
