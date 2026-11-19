package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

public class DropLittleCatsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double t = 0;
		double angl = 0;
		double yyy = 0;
		double xxx = 0;
		double zzz = 0;
		for (int index0 = 0; index0 < 6; index0++) {
			angl = Mth.nextDouble(RandomSource.create(), 0, 6.283);
			t = Mth.nextDouble(RandomSource.create(), 2, 6);
			xxx = x + t * Math.sin(angl);
			zzz = z + t * Math.cos(angl);
			yyy = FindValidYForCatProcedure.execute(world, x, y, z, xxx, y + 1, zzz);
			if (yyy < 114513) {
				SummonMyCatAtProcedure.execute(world, xxx, yyy, zzz, entity);
			}
		}
	}
}
