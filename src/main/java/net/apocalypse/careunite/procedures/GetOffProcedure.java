package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class GetOffProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.stopRiding();
	}
}
