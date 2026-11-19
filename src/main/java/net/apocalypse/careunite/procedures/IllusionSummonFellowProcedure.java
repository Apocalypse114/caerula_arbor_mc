package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

public class IllusionSummonFellowProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (Math.random() < 0.6) {
			RavagerSummonFellowsProcedure.execute(world, x, y, z, entity, 1);
		}
	}
}
