package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class Revive15SanityProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		RevivesPlayerSanityProcedure.execute(entity, 15);
	}
}
