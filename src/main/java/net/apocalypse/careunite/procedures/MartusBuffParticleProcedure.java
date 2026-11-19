package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

public class MartusBuffParticleProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity.tickCount % 5 == 0) {
			MartusParticleRim1Procedure.execute(world, x, y, z);
		}
	}
}
