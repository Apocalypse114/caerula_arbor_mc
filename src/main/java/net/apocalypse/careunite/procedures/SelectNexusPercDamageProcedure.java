package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class SelectNexusPercDamageProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.getPersistentData().putString("showcasingEvoNode", "nexus.perc_damage");
	}
}
