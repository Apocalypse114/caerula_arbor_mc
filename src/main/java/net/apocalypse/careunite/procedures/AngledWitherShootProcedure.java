package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

public class AngledWitherShootProcedure {
	public static void execute(LevelAccessor world, Entity entity, double angle) {
		if (entity == null)
			return;
		ShootWitherSkullProcedure.execute(world, entity, 0.1, Math.cos(Math.toRadians(angle)), Mth.nextDouble(RandomSource.create(), -0.25, 0.25), Math.sin(Math.toRadians(angle)), 5, 0.35, entity.getX(),
				entity.getY() + Mth.nextDouble(RandomSource.create(), 0.25, 3), entity.getZ());
	}
}
