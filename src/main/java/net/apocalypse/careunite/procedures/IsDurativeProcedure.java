package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.ComplexChitinGolemEntity;

public class IsDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof ComplexChitinGolemEntity _datEntI ? _datEntI.getEntityData().get(ComplexChitinGolemEntity.DATA_duration) : 0) <= 0;
	}
}
