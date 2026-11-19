package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class GetSanityProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		double modi = 0;
		modi = entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get())
				? _livingEntity0.getAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()).getValue()
				: 0;
		if (modi <= 0) {
			return "Infinity";
		}
		return ""
				+ ((int) ((entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY.get()) ? _livingEntity1.getAttribute(CaerulaArborModAttributes.SANITY.get()).getBaseValue() : 0)
						/ modi));
	}
}
