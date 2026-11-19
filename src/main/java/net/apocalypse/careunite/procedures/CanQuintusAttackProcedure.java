package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.BishopFishEntity;

public class CanQuintusAttackProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof BishopFishEntity _datEntI ? _datEntI.getEntityData().get(BishopFishEntity.DATA_duration) : 0) <= 0;
	}
}
