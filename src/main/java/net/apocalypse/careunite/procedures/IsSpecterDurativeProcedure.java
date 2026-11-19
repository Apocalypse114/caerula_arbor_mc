package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.SpecterEntity;

public class IsSpecterDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		if (entity.tickCount <= 15) {
			return false;
		}
		return (entity instanceof SpecterEntity _datEntI ? _datEntI.getEntityData().get(SpecterEntity.DATA_duration) : 0) <= 0;
	}
}
