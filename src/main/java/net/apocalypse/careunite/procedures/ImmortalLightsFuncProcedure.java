package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class ImmortalLightsFuncProcedure {
	public static void execute(Entity entity, double amplifier) {
		if (entity == null)
			return;
		double leftd = 0;
		if (EraceAllNegativeBuffProcedure.execute(entity)) {
			leftd = entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(CaerulaArborModMobEffects.IMMORTAL_LIGHTS.get()) ? _livEnt.getEffect(CaerulaArborModMobEffects.IMMORTAL_LIGHTS.get()).getDuration() : 0;
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(CaerulaArborModMobEffects.IMMORTAL_LIGHTS.get());
			if (amplifier > 0) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.IMMORTAL_LIGHTS.get(), (int) leftd, (int) (amplifier - 1)));
			}
		}
	}
}
