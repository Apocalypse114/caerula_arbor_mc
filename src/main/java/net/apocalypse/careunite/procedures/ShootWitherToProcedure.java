package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

public class ShootWitherToProcedure {
	public static void execute(LevelAccessor world, Entity from, Entity tgt) {
		if (from == null || tgt == null)
			return;
		double vx = 0;
		double vy = 0;
		double vz = 0;
		vx = tgt.getX() - from.getX();
		vy = (tgt.getY() + tgt.getBbHeight() * 0.5) - (from.getY() + 2.7);
		vz = tgt.getZ() - from.getZ();
		ShootWitherSkullProcedure.execute(world, from, 0.1, vx, vy, vz, 1, Mth.nextDouble(RandomSource.create(), 0.42, 0.56), from.getX(), from.getY() + 2.7, from.getZ());
	}
}
