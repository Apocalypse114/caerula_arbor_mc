package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.OceanizedEnderinaEntity;

public class InitEnderinaProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
			_livingEntity0.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).setBaseValue(85);
		if (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
			_livingEntity1.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).setBaseValue(4);
		if (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()))
			_livingEntity2.getAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()).setBaseValue(0.0125);
		if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RESISTANCE.get()))
			_livingEntity3.getAttribute(CaerulaArborModAttributes.SANITY_RESISTANCE.get()).setBaseValue(75);
		if (entity instanceof OceanizedEnderinaEntity) {
			((OceanizedEnderinaEntity) entity).setAnimation("animation.oceanized_enderina.start");
		}
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 50, 9, false, false));
	}
}
