package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class InitShooterSanityProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get()))
			_livingEntity1.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get())
					.setBaseValue(((entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get())
							? _livingEntity0.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get()).getBaseValue()
							: 0) + 12));
	}
}
