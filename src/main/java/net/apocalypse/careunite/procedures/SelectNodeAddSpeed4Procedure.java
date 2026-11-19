package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class SelectNodeAddSpeed4Procedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.getPersistentData().putString("showcasingEvoNode", "node.add_speed.4");
	}
}
