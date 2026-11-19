package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class GetDefenseProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		double d = 0;
		d = entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()) ? _livingEntity0.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).getValue() : 0;
		if (d > 0) {
			return new java.text.DecimalFormat("##.#").format(d);
		}
		return "";
	}
}
