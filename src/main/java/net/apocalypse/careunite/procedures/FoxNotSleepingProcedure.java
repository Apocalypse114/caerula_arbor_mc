package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedFoxEntity;

public class FoxNotSleepingProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return !(entity instanceof OceanizedFoxEntity _datEntL0 && _datEntL0.getEntityData().get(OceanizedFoxEntity.DATA_sleeping))
				&& (entity instanceof OceanizedFoxEntity _datEntI ? _datEntI.getEntityData().get(OceanizedFoxEntity.DATA_duration) : 0) <= 0;
	}
}
