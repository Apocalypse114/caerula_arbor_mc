package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class InitFlamarineStatueProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
			_livingEntity0.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).setBaseValue(15);
		if (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
			_livingEntity1.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).setBaseValue(80);
		if (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RESISTANCE.get()))
			_livingEntity2.getAttribute(CaerulaArborModAttributes.SANITY_RESISTANCE.get()).setBaseValue(50);
	}
}
