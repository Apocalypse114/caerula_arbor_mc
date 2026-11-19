package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class EatColleProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		GiveLessArmorProcedure.execute(entity, 0);
	}
}
