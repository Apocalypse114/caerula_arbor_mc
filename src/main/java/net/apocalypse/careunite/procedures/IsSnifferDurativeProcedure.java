package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedSnifferEntity;

public class IsSnifferDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof OceanizedSnifferEntity _datEntI ? _datEntI.getEntityData().get(OceanizedSnifferEntity.DATA_duration) : 0) <= 0;
	}
}
