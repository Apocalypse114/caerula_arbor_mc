package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedChickenEntity;

public class GetChickenSizeProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		if (entity instanceof OceanizedChickenEntity _datEntL0 && _datEntL0.getEntityData().get(OceanizedChickenEntity.DATA_IS_CHILD)) {
			return 0.5;
		}
		return 1;
	}
}
