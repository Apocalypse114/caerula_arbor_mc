package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

public class GetHealthPercProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		return (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
	}
}
