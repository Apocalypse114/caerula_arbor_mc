package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class EatCellJellyProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		RevivesPlayerLightsProcedure.execute(entity, 7);
		DeductPlayerSanityProcedure.execute(entity, 75);
	}
}
