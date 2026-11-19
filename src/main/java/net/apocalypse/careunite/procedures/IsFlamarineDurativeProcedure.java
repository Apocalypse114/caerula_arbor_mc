package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.FlamarineGolemEntity;

public class IsFlamarineDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof FlamarineGolemEntity _datEntI ? _datEntI.getEntityData().get(FlamarineGolemEntity.DATA_duration) : 0) <= 0;
	}
}
