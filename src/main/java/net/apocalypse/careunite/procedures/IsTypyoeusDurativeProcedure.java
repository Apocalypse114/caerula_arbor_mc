package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.TyphoeusEntity;

public class IsTypyoeusDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof TyphoeusEntity _datEntI ? _datEntI.getEntityData().get(TyphoeusEntity.DATA_DURATION) : 0) <= 0;
	}
}
