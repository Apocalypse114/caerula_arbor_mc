package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class SkadiSwordBonusProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (GetHealthPercProcedure.execute(entity) >= 0.5) {
			if (!(entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(CaerulaArborModMobEffects.BOOST_OF_SILENCE.get()))) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.BOOST_OF_SILENCE.get(), 10, 6, false, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_REACH.get(), 10, 2, false, false));
			}
		}
	}
}
