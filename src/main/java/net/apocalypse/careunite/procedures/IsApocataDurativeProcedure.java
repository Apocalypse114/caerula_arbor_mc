package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.ApocataEntity;

public class IsApocataDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof ApocataEntity _datEntI ? _datEntI.getEntityData().get(ApocataEntity.DATA_duration) : 0) <= 0;
	}
}
