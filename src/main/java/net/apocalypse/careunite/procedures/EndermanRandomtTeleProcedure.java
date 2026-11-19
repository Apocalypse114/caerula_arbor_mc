package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

public class EndermanRandomtTeleProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double tX = 0;
		double tZ = 0;
		double validY = 0;
		double dist = 0;
		double ratio = 0;
		double angl = 0;
		if (entity.isAlive()) {
			for (int index0 = 0; index0 < 64; index0++) {
				angl = Mth.nextDouble(RandomSource.create(), 0, 6.283);
				dist = Mth.nextDouble(RandomSource.create(), 3, 6);
				tX = x + dist * Math.sin(angl);
				tZ = z + dist * Math.cos(angl);
				validY = FindValidYProcedure.execute(world, tX, y, tZ);
				if (validY <= 114513) {
					TeleportToProcedure.execute(world, entity, x, y, z, tX, validY, tZ);
					break;
				}
			}
		}
	}
}
