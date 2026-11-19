package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.IzumikEntity;

public class InitIzumikProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof IzumikEntity) {
			((IzumikEntity) entity).setAnimation("animation.izumik.start");
		}
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 30, 1, false, false));
		if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
			_livingEntity3.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
					.setBaseValue(((entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
							? _livingEntity2.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).getBaseValue()
							: 0) + 10));
		if (entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
			_livingEntity5.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
					.setBaseValue(((entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
							? _livingEntity4.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).getBaseValue()
							: 0) + 50));
		if (entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()))
			_livingEntity6.getAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()).setBaseValue(0.01);
	}
}
