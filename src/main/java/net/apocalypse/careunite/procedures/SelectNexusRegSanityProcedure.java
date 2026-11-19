package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class SelectNexusRegSanityProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.getPersistentData().putString("showcasingEvoNode", "nexus.reg_sanity");
	}
}
