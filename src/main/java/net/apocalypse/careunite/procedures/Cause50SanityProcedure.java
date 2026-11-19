package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class Cause50SanityProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		DeductPlayerSanityProcedure.execute(entity, 50);
	}
}
