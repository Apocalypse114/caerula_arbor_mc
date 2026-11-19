package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class MereHealOneSanityProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity.tickCount % 5 == 0) {
			if (GetOceanizeIndexProcedure.execute(entity) >= 2.5) {
				RevivesPlayerSanityProcedure.execute(entity, 1);
			} else {
				DeductPlayerSanityProcedure.execute(entity, 1);
			}
		}
	}
}
