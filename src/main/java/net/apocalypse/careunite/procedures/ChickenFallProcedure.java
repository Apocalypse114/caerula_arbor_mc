package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class ChickenFallProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.push(0, (-0.64), 0);
	}
}
