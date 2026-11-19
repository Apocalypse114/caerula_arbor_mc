package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class SetVulnerbleProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.invulnerableTime = 0;
	}
}
