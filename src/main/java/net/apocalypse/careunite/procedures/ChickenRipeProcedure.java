package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedChickenEntity;

public class ChickenRipeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return !(entity instanceof OceanizedChickenEntity _datEntL0 && _datEntL0.getEntityData().get(OceanizedChickenEntity.DATA_IS_CHILD));
	}
}
