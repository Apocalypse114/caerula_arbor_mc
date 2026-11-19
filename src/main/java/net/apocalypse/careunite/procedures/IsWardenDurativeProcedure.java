package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedWardenEntity;

public class IsWardenDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return (entity instanceof OceanizedWardenEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWardenEntity.DATA_duration) : 0) <= 0;
	}
}
