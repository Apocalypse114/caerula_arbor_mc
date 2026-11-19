package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class IsValidEnemyForIshmaelProcedure {
	public static boolean execute(Entity ene, Entity entity) {
		if (ene == null || entity == null)
			return false;
		Entity enemy = null;
		double damage = 0;
		double r = 0;
		return IsValidEnemyForIreneProcedure.execute(ene, entity) && !(ene instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(CaerulaArborModMobEffects.TRAPPED.get()))
				&& !(ene instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(CaerulaArborModMobEffects.INVULNERABLE.get()));
	}
}
