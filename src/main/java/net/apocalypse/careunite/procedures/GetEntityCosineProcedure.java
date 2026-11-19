package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class GetEntityCosineProcedure {
	public static double execute(Entity A, Entity B) {
		if (A == null || B == null)
			return 0;
		return GetCosineProcedure.execute(B.getX() - A.getX(), B.getZ() - A.getZ(), A.getLookAngle().x, A.getLookAngle().z);
	}
}
