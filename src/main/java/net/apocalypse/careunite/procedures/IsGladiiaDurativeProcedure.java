package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.GladiiaEntity;

public class IsGladiiaDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof GladiiaEntity _datEntI ? _datEntI.getEntityData().get(GladiiaEntity.DATA_duration) : 0) <= 0;
	}
}
