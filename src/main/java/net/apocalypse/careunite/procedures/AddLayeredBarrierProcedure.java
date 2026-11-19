package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class AddLayeredBarrierProcedure {
	public static void execute(Entity entity, double amount) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get()))
			_livingEntity1.getAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get())
					.setBaseValue(((entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get())
							? _livingEntity0.getAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get()).getBaseValue()
							: 0) + amount));
	}
}
