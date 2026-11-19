package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class IsSameTypeProcedure {
	public static boolean execute(Entity a, Entity b) {
		if (a == null || b == null)
			return false;
		return a.getType() == b.getType();
	}
}
