package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class MobAliveProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return entity.isAlive();
	}
}
