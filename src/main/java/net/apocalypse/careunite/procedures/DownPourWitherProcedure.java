package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

public class DownPourWitherProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double dist = 0;
		double rng = 0;
		dist = Mth.nextDouble(RandomSource.create(), 0, 16);
		rng = Mth.nextDouble(RandomSource.create(), 0, 6.283);
		ShootWitherSkullProcedure.execute(world, entity, 0.15, 0, -1, 0, 6, 0.35, x + dist * Math.cos(rng), y + Mth.nextDouble(RandomSource.create(), 4, 8), z + dist * Math.sin(rng));
	}
}
