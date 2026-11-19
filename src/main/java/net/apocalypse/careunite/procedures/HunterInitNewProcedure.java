package net.apocalypse.careunite.procedures;

import net.minecraftforge.common.ForgeMod;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class HunterInitNewProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(ForgeMod.SWIM_SPEED.get()))
			_livingEntity1.getAttribute(ForgeMod.SWIM_SPEED.get())
					.setBaseValue(((entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(ForgeMod.SWIM_SPEED.get()) ? _livingEntity0.getAttribute(ForgeMod.SWIM_SPEED.get()).getBaseValue() : 0) * 8));
		if (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()))
			_livingEntity2.getAttribute(CaerulaArborModAttributes.SANITY_MODIFIER.get()).setBaseValue(0.33);
	}
}
