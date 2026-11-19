package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.OceanizedLlamaEntity;

public class SetLLamaChargedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (!(entity instanceof OceanizedLlamaEntity _datEntL0 && _datEntL0.getEntityData().get(OceanizedLlamaEntity.DATA_CHARGED))) {
			if (entity instanceof OceanizedLlamaEntity _datEntSetL)
				_datEntSetL.getEntityData().set(OceanizedLlamaEntity.DATA_CHARGED, true);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.REGENERATION_PERCLY.get(), 200, 4, false, false));
			if (entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
				_livingEntity4.getAttribute(Attributes.MAX_HEALTH)
						.setBaseValue(((entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity3.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0) * 1.6));
			if (entity instanceof LivingEntity _entity)
				_entity.setHealth(entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
		}
	}
}
