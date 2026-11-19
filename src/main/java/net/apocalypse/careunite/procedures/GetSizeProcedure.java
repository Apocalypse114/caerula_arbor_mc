package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class GetSizeProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		return entity.getBbWidth() * entity.getBbHeight();
	}
}
