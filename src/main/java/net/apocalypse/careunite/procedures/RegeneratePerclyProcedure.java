package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

public class RegeneratePerclyProcedure {
	public static void execute(Entity entity, double amplifier) {
		if (entity == null)
			return;
		LivingHeal1Procedure.execute(entity, (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.0025 * (amplifier + 1));
	}
}
