package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.PromotedAxolotlEntity;

public class SetAxolotlTextureProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double rrr = 0;
		rrr = Math.random();
		if (rrr > 0.45) {
			if (rrr < 0.9) {
				if (entity instanceof PromotedAxolotlEntity animatable)
					animatable.setTexture("axolotl_nethersea");
			} else {
				if (entity instanceof PromotedAxolotlEntity animatable)
					animatable.setTexture("axolotl_cellular");
			}
		}
		if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
			_livingEntity3.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
					.setBaseValue(((entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
							? _livingEntity2.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).getBaseValue()
							: 0) + 45));
	}
}
