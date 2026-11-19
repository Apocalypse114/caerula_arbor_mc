package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class BruteAxeHitProcedure {
	public static void execute(Entity sourceentity) {
		if (sourceentity == null)
			return;
		double amp = 0;
		if (sourceentity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(CaerulaArborModMobEffects.BRUTE_POWER.get())) {
			amp = Math.min(sourceentity instanceof LivingEntity _livEnt && _livEnt.hasEffect(CaerulaArborModMobEffects.BRUTE_POWER.get()) ? _livEnt.getEffect(CaerulaArborModMobEffects.BRUTE_POWER.get()).getAmplifier() : 0, 5);
			if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.BRUTE_POWER.get(), 100, (int) (amp + 1)));
		} else {
			if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.BRUTE_POWER.get(), 100, 0));
		}
	}
}
