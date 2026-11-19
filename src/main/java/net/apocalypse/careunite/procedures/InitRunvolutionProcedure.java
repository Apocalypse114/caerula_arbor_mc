package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.RunvolutionEntity;

public class InitRunvolutionProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof RunvolutionEntity) {
			((RunvolutionEntity) entity).setAnimation("animation.runvolution.merge");
		}
	}
}
