package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.MoistEnderCrystalEntity;

public class CheckCrystalStabilityProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return entity instanceof MoistEnderCrystalEntity;
	}
}
