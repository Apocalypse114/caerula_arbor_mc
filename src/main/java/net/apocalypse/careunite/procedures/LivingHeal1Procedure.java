package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class LivingHeal1Procedure {
	public static void execute(Entity entity, double amount) {
		LivingHealProcedure.execute(entity, amount);
	}
}
