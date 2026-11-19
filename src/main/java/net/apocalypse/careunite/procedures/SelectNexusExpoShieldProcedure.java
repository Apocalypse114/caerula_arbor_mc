package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class SelectNexusExpoShieldProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.getPersistentData().putString("showcasingEvoNode", "nexus.expo_shield");
	}
}
