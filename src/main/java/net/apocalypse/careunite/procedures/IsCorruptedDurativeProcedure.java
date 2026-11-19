package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.SkadiCorruptedEntity;

public class IsCorruptedDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof SkadiCorruptedEntity _datEntI ? _datEntI.getEntityData().get(SkadiCorruptedEntity.DATA_duration) : 0) <= 0;
	}
}
