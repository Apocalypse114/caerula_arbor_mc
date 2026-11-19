package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class OnLivingBarrierFuncProcedure {
	public static double execute(DamageSource damagesource, Entity entity, double amount) {
		if (damagesource == null || entity == null)
			return 0;
		double amount1 = 0;
		Entity sEnt = null;
		amount1 = amount;
		sEnt = damagesource.getEntity();
		if (entity instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(CaerulaArborModMobEffects.SEABORN_DECREASE.get())) {
			amount1 = amount1 * 2;
		}
		return amount1;
	}
}
