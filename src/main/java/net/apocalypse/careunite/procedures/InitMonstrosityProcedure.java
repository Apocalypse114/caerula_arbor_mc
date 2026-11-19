package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class InitMonstrosityProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
			_livingEntity1.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
					.setBaseValue(((entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
							? _livingEntity0.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).getBaseValue()
							: 0) + 15));
		if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
			_livingEntity3.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
					.setBaseValue(((entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
							? _livingEntity2.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).getBaseValue()
							: 0) + 25));
	}
}
