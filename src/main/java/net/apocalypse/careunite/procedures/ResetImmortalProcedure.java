package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class ResetImmortalProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.getPersistentData().putBoolean("immortalTriggered", false);
	}
}
