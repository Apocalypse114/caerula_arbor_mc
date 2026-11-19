package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class SnifferEggCounterProcedure {
	public static boolean execute(Entity sourceentity) {
		if (sourceentity == null)
			return true;
		if (Math.random() < 0.25) {
			if (!IsPalsyProcedure.execute(sourceentity)) {
				if (sourceentity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.NUMB.get()))
					_livingEntity0.getAttribute(CaerulaArborModAttributes.NUMB.get()).setBaseValue(1);
				return false;
			}
		}
		return true;
	}
}
