package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedIllusionerEntity;

public class IsIllusionerDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof OceanizedIllusionerEntity _datEntI ? _datEntI.getEntityData().get(OceanizedIllusionerEntity.DATA_duration) : 0) <= 0;
	}
}
