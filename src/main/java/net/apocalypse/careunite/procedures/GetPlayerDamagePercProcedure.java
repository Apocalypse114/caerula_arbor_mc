package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

public class GetPlayerDamagePercProcedure {
	public static double execute(Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return 0;
		double hitTime = 0;
		double perc = 0;
		hitTime = sourceentity.getPersistentData().getDouble("playerEvoHitTime");
		if (entity == ((sourceentity instanceof LivingEntity _entity) ? _entity.getLastHurtMob() : null)) {
			perc = Math.min((int) ((hitTime - 1) / 2) * 0.005, 0.25);
			sourceentity.getPersistentData().putDouble("playerEvoHitTime", (hitTime + 1));
		} else {
			sourceentity.getPersistentData().putDouble("playerEvoHitTime", 0);
			return 0;
		}
		return perc * (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
	}
}
