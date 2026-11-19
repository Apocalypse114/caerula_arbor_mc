package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.NoviceAlchemistEntity;

public class IsNoviceDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof NoviceAlchemistEntity _datEntI ? _datEntI.getEntityData().get(NoviceAlchemistEntity.DATA_DURATION) : 0) <= 0;
	}
}
