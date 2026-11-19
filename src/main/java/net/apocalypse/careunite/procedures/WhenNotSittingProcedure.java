package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedDogEntity;

public class WhenNotSittingProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return !(entity instanceof OceanizedDogEntity _datEntL0 && _datEntL0.getEntityData().get(OceanizedDogEntity.DATA_sitting));
	}
}
