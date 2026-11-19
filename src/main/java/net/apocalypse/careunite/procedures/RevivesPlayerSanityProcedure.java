package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class RevivesPlayerSanityProcedure {
	public static void execute(Entity player, double num) {
		if (player == null)
			return;
		double snt = 0;
		if (num > 0) {
			snt = (player instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY.get()) ? _livingEntity0.getAttribute(CaerulaArborModAttributes.SANITY.get()).getBaseValue() : 0)
					+ num * (player instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get())
							? _livingEntity1.getAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()).getValue()
							: 0);
			if (snt > 1000) {
				snt = 1000;
			}
			if (player instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY.get()))
				_livingEntity2.getAttribute(CaerulaArborModAttributes.SANITY.get()).setBaseValue(snt);
		}
	}
}
