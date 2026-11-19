package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.CaerulaArborMod;

public class LumenPerformHealProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		CaerulaArborMod.queueServerWork(8, () -> {
			if (entity.isAlive()) {
				LumenCommonHealProcedure.execute(world, x, y, z, entity);
			}
		});
	}
}
