package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.XantisEntity;

public class IsXantisTapativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof XantisEntity _datEntI ? _datEntI.getEntityData().get(XantisEntity.DATA_TAP_TICK) : 0) <= 0;
	}
}
