package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class GetPalsyProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		return "" + Math.round(entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.NUMB.get()) ? _livingEntity0.getAttribute(CaerulaArborModAttributes.NUMB.get()).getBaseValue() : 0);
	}
}
