package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class HitEntWithPotionProcedure {
	public static void execute(Entity entity, Entity immediatesourceentity) {
		if (entity == null || immediatesourceentity == null)
			return;
		ShootRandomPotionProcedure.execute(entity);
		if (!immediatesourceentity.level().isClientSide())
			immediatesourceentity.discard();
	}
}
