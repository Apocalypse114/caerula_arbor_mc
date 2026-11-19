package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class SelectNodeLessArmor2Procedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.getPersistentData().putString("showcasingEvoNode", "node.less_armor.2");
	}
}
