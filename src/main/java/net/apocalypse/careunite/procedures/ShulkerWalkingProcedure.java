package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedShulkerEntity;

public class ShulkerWalkingProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (entity.isAlive()) {
			return entity instanceof OceanizedShulkerEntity _datEntL1 && _datEntL1.getEntityData().get(OceanizedShulkerEntity.DATA_WALKING);
		}
		return false;
	}
}
