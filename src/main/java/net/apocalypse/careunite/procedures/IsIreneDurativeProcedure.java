package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.IreneEntity;

public class IsIreneDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof IreneEntity _datEntI ? _datEntI.getEntityData().get(IreneEntity.DATA_duration) : 0) <= 0;
	}
}
