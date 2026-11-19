package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.IsharmlaEntity;

public class IsIsharmlaDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof IsharmlaEntity _datEntI ? _datEntI.getEntityData().get(IsharmlaEntity.DATA_DURATION) : 0) <= 0;
	}
}
