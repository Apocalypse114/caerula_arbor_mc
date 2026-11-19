package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class ChitinGolemInitProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()))
			_livingEntity0.getAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()).setBaseValue(0.05);
		if (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get()))
			_livingEntity1.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get()).setBaseValue(10);
		if (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.MISSRATE.get()))
			_livingEntity2.getAttribute(CaerulaArborModAttributes.MISSRATE.get()).setBaseValue(33);
	}
}
