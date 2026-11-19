package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

public class AngledWitheriaShootProcedure {
	public static void execute(LevelAccessor world, Entity entity, double angle) {
		if (entity == null)
			return;
		double dist = 0;
		dist = Mth.nextDouble(RandomSource.create(), 1.25, 5);
		ShootWitherSkullProcedure.execute(world, entity, 0.1, Math.cos(Math.toRadians(angle + 90)), Mth.nextDouble(RandomSource.create(), -0.5, 0.5), Math.sin(Math.toRadians(angle + 90)), 5, 0.35,
				entity.getX() + dist * Math.cos(Math.toRadians(angle)), entity.getY() + Mth.nextDouble(RandomSource.create(), 1.25, 4.25), entity.getZ() + dist * Math.sin(Math.toRadians(angle)));
	}
}
