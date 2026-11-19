package net.apocalypse.careunite.procedures;

import net.minecraftforge.common.ForgeMod;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

public class HelperFastSwimProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(ForgeMod.SWIM_SPEED.get()))
			_livingEntity1.getAttribute(ForgeMod.SWIM_SPEED.get())
					.setBaseValue(((entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED) ? _livingEntity0.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue() : 0) * 10));
	}
}
