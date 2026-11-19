package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizeRabbitEntity;

public class RabbitSelectVariantProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double variant = 0;
		double rrr = 0;
		double curVar = 0;
		curVar = entity instanceof OceanizeRabbitEntity _datEntI ? _datEntI.getEntityData().get(OceanizeRabbitEntity.DATA_variant) : 0;
		if (curVar == 0) {
			rrr = Math.random();
			if (rrr < 0.35) {
				variant = 0;
			} else if (rrr < 0.7) {
				variant = 2;
			} else if (rrr < 0.82) {
				variant = 1;
			} else if (rrr < 0.94) {
				variant = 4;
			} else {
				variant = 3;
			}
			if (variant != 0) {
				if (entity instanceof OceanizeRabbitEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizeRabbitEntity.DATA_variant, (int) variant);
				if (variant != 2) {
					if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Attributes.MAX_HEALTH))
						_livingEntity3.getAttribute(Attributes.MAX_HEALTH)
								.setBaseValue(((entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Attributes.MAX_HEALTH) ? _livingEntity2.getAttribute(Attributes.MAX_HEALTH).getBaseValue() : 0) * 1.25));
					if (entity instanceof LivingEntity _entity)
						_entity.setHealth(entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
				}
				if (variant == 3) {
					if (entity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
						_livingEntity7.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(
								((entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity6.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0) * 1.25));
				}
			}
		} else {
			variant = curVar;
		}
		if (entity instanceof OceanizeRabbitEntity animatable)
			animatable.setTexture(("oceanized_rabbit_" + (int) variant));
	}
}
