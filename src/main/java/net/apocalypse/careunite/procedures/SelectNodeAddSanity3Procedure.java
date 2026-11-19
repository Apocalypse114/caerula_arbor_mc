package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class SelectNodeAddSanity3Procedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.getPersistentData().putString("showcasingEvoNode", "node.add_sanity.3");
	}
}
