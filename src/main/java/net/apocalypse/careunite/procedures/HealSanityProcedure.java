package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class HealSanityProcedure {
	public static void execute(Entity entity, double amplifier) {
		if (entity == null)
			return;
		double newsanity = 0;
		RevivesPlayerSanityProcedure.execute(entity, 100 * (amplifier + 1));
	}
}
