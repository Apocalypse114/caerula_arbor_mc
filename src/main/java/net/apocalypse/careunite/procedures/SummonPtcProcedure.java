package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

public class SummonPtcProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity immediatesourceentity) {
		if (immediatesourceentity == null)
			return;
		Entity enemy = null;
		immediatesourceentity.setNoGravity(true);
		if (immediatesourceentity.tickCount > 160) {
			if (!immediatesourceentity.level().isClientSide())
				immediatesourceentity.discard();
		}
		BulletParticleProcedure.execute(world, x, y, z);
	}
}
