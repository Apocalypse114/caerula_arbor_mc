package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.ThirsterEntity;

public class IsThirsterDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof ThirsterEntity _datEntI ? _datEntI.getEntityData().get(ThirsterEntity.DATA_DURATION) : 0) <= 0;
	}
}
