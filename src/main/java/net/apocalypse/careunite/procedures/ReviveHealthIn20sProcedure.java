package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

public class ReviveHealthIn20sProcedure {
	public static void execute(Entity entity, double amplifier) {
		if (entity == null)
			return;
		if (entity.isAlive()) {
			if (entity instanceof LivingEntity _entity)
				_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.025 * (amplifier + 1)));
		}
	}
}
