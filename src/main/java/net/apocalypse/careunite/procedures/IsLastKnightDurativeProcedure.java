package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.TheLastKnightEntity;

public class IsLastKnightDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof TheLastKnightEntity _datEntI ? _datEntI.getEntityData().get(TheLastKnightEntity.DATA_duration) : 0) <= 0;
	}
}
