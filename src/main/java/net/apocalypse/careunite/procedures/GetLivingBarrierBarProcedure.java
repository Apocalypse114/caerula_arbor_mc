package net.apocalypse.careunite.procedures;

import org.checkerframework.checker.units.qual.h;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class GetLivingBarrierBarProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		double d = 0;
		double h = 0;
		d = entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.LIVING_BARRIER.get()) ? _livingEntity0.getAttribute(CaerulaArborModAttributes.LIVING_BARRIER.get()).getBaseValue() : 0;
		h = entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1;
		if (h <= 0) {
			return 0;
		}
		d = d / h;
		if (d <= 1) {
			return d * 25;
		}
		return Math.min((1 + Math.log10(d)) * 25, 50);
	}
}
