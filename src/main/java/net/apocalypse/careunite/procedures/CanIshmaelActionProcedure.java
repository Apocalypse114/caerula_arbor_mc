package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.IshmaelEntity;

public class CanIshmaelActionProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof IshmaelEntity _datEntI ? _datEntI.getEntityData().get(IshmaelEntity.DATA_DURATION) : 0) <= 0 && (entity instanceof IshmaelEntity _datEntI ? _datEntI.getEntityData().get(IshmaelEntity.DATA_SKILL_P) : 0) >= 0;
	}
}
