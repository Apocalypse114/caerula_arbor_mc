package net.apocalypse.careunite.procedures;

import net.minecraftforge.common.ForgeMod;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class GladiiaInitProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
			_livingEntity0.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).setBaseValue(20);
		if (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(ForgeMod.SWIM_SPEED.get()))
			_livingEntity2.getAttribute(ForgeMod.SWIM_SPEED.get())
					.setBaseValue(((entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(ForgeMod.SWIM_SPEED.get()) ? _livingEntity1.getAttribute(ForgeMod.SWIM_SPEED.get()).getBaseValue() : 0) * 14));
		if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()))
			_livingEntity3.getAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()).setBaseValue(0.33);
	}
}
