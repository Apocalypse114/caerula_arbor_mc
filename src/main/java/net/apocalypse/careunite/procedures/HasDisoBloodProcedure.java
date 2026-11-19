package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class HasDisoBloodProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return IsRejectionOnProcedure.execute(entity, 1);
	}
}
