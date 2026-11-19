package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class SelectEunectes3Procedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.getPersistentData().putString("showcasingEvoNode", "node.eunectes.3");
	}
}
