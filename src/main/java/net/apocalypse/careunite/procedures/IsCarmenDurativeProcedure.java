package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.SaintCarmenEntity;

public class IsCarmenDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof SaintCarmenEntity _datEntI ? _datEntI.getEntityData().get(SaintCarmenEntity.DATA_duration) : 0) <= 0;
	}
}
