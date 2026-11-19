package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

public class DoBeaconForwardLaserProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double dist) {
		if (entity == null)
			return;
		double dx = 0;
		double dz = 0;
		double len = 0;
		dx = entity.getLookAngle().x;
		dz = entity.getLookAngle().z;
		len = Math.sqrt(dx * dx + dz * dz);
		if (len > 0.001) {
			dx = dx / len;
			dz = dz / len;
			DoLaserRangedProcedure.execute(world, x + dx * dist, y, z + dz * dist, entity);
		}
	}
}
