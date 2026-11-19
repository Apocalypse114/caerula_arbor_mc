package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.LastKnightAndHorseEntity;

public class IsLastKnightStartingProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return entity.tickCount >= 40 && (entity instanceof LastKnightAndHorseEntity _datEntI ? _datEntI.getEntityData().get(LastKnightAndHorseEntity.DATA_duration) : 0) <= 0;
	}
}
