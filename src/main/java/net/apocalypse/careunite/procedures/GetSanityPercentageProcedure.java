package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

public class GetSanityPercentageProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return -1
;
		if (entity instanceof LivingEntity _livingEntity0){
			AttributeInstance inst = _livingEntity0.getAttribute(CaerulaArborModAttributes.SANITY.get());
			if (inst == null) return -1;
 			return inst.getBaseValue() / 1000;
		}
		return -1;
	}
}
