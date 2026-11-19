package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class GetMagicResistanceBarProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		double d = 0;
		d = entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()) ? _livingEntity0.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).getValue() : 0;
		return d * 0.25;
	}
}
