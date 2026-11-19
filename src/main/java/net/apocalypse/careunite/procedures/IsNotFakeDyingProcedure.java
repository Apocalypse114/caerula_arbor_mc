package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class IsNotFakeDyingProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return !(entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(CaerulaArborModMobEffects.FAKE_DEATH.get()));
	}
}
