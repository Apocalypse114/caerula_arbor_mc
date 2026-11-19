package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class GetSpeedProcedure {
	public static double execute(Entity e) {
		if (e == null)
			return 0;
		return Math.sqrt(e.getDeltaMovement().x() * e.getDeltaMovement().x() + e.getDeltaMovement().y() * e.getDeltaMovement().y() + e.getDeltaMovement().z() * e.getDeltaMovement().z());
	}
}
