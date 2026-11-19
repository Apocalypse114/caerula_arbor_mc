package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class ClearAllEffectsProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity.tickCount % 10 == 0) {
			entity.clearFire();
			if (entity instanceof LivingEntity _entity)
				_entity.removeAllEffects();
			if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(CaerulaArborModAttributes.NUMB.get()))
				_livingEntity3.getAttribute(CaerulaArborModAttributes.NUMB.get()).setBaseValue(0);
			if (entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY.get()))
				_livingEntity4.getAttribute(CaerulaArborModAttributes.SANITY.get()).setBaseValue(1000);
		}
	}
}
