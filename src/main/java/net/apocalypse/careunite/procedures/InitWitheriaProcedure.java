package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.OceannizedWitheriaEntity;

public class InitWitheriaProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()))
			_livingEntity0.getAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()).setBaseValue(0.01);
		if (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
			_livingEntity1.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).setBaseValue(65);
		if (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
			_livingEntity2.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).setBaseValue(5);
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 95, 9, false, false));
		if (entity instanceof OceannizedWitheriaEntity) {
			((OceannizedWitheriaEntity) entity).setAnimation("animation.oceanzied_witheria.start");
		}
		if (entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get()))
			_livingEntity6.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get())
					.setBaseValue(((entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get())
							? _livingEntity5.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get()).getBaseValue()
							: 0) + 10));
	}
}
