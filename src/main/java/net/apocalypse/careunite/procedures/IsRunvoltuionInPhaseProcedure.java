package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.RunvolutionEntity;

public class IsRunvoltuionInPhaseProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return (entity instanceof RunvolutionEntity r) ? r.getPhase() > 0: false;
	}
}
