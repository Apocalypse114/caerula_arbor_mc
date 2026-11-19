package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.RunvolutionEntity;

public class IsRunvolutionDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof RunvolutionEntity _datEntI ? _datEntI.getEntityData().get(RunvolutionEntity.DATA_DURATION) : 0) <= 0;
	}
}
