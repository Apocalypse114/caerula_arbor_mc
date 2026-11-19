package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class GiveGuideLayProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5) {
			if (!(entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(CaerulaArborModMobEffects.MUTE.get()))) {
				if (!(entity instanceof LivingEntity _livEnt3 && _livEnt3.hasEffect(CaerulaArborModMobEffects.GUIDE_PATH_AHEAD.get()))) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.GUIDE_PATH_AHEAD.get(), 20, 0));
				}
			}
		}
	}
}
