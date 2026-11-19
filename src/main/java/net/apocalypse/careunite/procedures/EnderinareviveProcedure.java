package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.OceanizedEnderinaEntity;

public class EnderinareviveProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof OceanizedEnderinaEntity _datEntSetI)
			_datEntSetI.getEntityData().set(OceanizedEnderinaEntity.DATA_REVIVE_TICK, 200);
		if (entity instanceof OceanizedEnderinaEntity _datEntSetI)
			_datEntSetI.getEntityData().set(OceanizedEnderinaEntity.DATA_PHASE, 1);
		if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
			_livingEntity3.getAttribute(Attributes.ATTACK_DAMAGE)
					.setBaseValue(((entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity2.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0) * 2));
		if (entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
			_livingEntity5.getAttribute(Attributes.MAX_HEALTH)
					.setBaseValue(((entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity4.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0) * 3));
		if (entity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
			_livingEntity7.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
					.setBaseValue(((entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
							? _livingEntity6.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).getBaseValue()
							: 0) * 2));
		if (entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
			_livingEntity9.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
					.setBaseValue(((entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
							? _livingEntity8.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).getBaseValue()
							: 0) + 20));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 200, 1, false, false));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FAKE_DEATH.get(), 200, 1, false, false));
	}
}
