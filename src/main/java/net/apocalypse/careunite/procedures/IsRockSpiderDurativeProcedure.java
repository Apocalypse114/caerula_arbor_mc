package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.TideutantRockSpiderEntity;

public class IsRockSpiderDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof TideutantRockSpiderEntity _datEntI ? _datEntI.getEntityData().get(TideutantRockSpiderEntity.DATA_duration) : 0) <= 0;
	}
}
