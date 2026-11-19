package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class IsBishopStartedProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return entity.tickCount >= 80 && CanQuintusAttackProcedure.execute(entity);
	}
}
