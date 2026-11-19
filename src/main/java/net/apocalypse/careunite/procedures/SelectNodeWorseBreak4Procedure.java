package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class SelectNodeWorseBreak4Procedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.getPersistentData().putString("showcasingEvoNode", "node.worse_break.4");
	}
}
