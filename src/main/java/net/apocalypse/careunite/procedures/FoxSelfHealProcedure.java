package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

public class FoxSelfHealProcedure {
	public static void execute(Entity sourceentity) {
		if (sourceentity == null)
			return;
		Entity enemy = null;
		double damage = 0;
		if (sourceentity instanceof LivingEntity _entity)
			_entity.setHealth((float) ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + 2));
	}
}
