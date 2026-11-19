package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.OceanizedWardenisEntity;

public class InitWardenProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
			_livingEntity0.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).setBaseValue(75);
		if (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()))
			_livingEntity1.getAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()).setBaseValue(0.01);
		if (entity instanceof OceanizedWardenisEntity) {
			((OceanizedWardenisEntity) entity).setAnimation("animation.oceanized_wardenis.start");
		}
		if (entity instanceof OceanizedWardenisEntity) {
			if (entity instanceof OceanizedWardenisEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedWardenisEntity.DATA_duration, 80);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 80, 5, false, false));
		}
	}
}
