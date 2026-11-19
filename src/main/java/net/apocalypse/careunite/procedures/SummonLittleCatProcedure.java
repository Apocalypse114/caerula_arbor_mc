package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

import net.apocalypse.careunite.init.CaerulaArborModGameRules;

public class SummonLittleCatProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double t = 0;
		double angl = 0;
		if (entity.tickCount % 400 == 40) {
			if (GetSeabornAroundProcedure.execute(world, x, y, z, entity) < (world.getLevelData().getGameRules().getInt(CaerulaArborModGameRules.CLONE_NUMBER_LIMIT))) {
				t = Mth.nextInt(RandomSource.create(), 2, 4);
				for (int index0 = 0; index0 < (int) t; index0++) {
					angl = Mth.nextDouble(RandomSource.create(), 0, 6.283);
					SummonMyCatAtProcedure.execute(world, x + 4 * Math.sin(angl), y + 1, z + 4 * Math.cos(angl), entity);
				}
			}
		}
	}
}
