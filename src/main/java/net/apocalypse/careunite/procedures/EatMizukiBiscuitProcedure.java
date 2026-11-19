package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class EatMizukiBiscuitProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_DEF_TINY.get(), 640, 15, false, false));
		RevivesPlayerLightsProcedure.execute(entity, 10);
		if (Math.random() < 0.5) {
			if (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get()))
				_livingEntity2.getAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get())
						.setBaseValue(Math.max(1,
								entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get())
										? _livingEntity1.getAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get()).getBaseValue()
										: 0));
		}
	}
}
