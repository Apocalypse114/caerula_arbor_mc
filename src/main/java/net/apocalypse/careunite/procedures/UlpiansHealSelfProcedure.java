package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

public class UlpiansHealSelfProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double healAmoun = 0;
		if (entity.isAlive()) {
			if (entity.invulnerableTime <= 15) {
				healAmoun = 8;
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.6) {
					healAmoun = 12;
				}
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth((float) Math.min((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + healAmoun, entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1));
			}
		}
	}
}
