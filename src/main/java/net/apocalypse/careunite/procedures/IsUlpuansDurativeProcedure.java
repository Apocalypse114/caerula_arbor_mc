package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.UlpiansEntity;

public class IsUlpuansDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof UlpiansEntity _datEntI ? _datEntI.getEntityData().get(UlpiansEntity.DATA_duration) : 0) <= 0;
	}
}
