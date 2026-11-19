package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.TideChimeraEntity;

public class IsChimeraDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		if (entity.tickCount <= 100) {
			return false;
		}
		return (entity instanceof TideChimeraEntity _datEntI ? _datEntI.getEntityData().get(TideChimeraEntity.DATA_duration) : 0) <= 0;
	}
}
