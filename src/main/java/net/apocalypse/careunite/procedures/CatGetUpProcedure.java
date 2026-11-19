package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedCatEntity;

public class CatGetUpProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.setShiftKeyDown(false);
		if (entity instanceof OceanizedCatEntity _datEntSetL)
			_datEntSetL.getEntityData().set(OceanizedCatEntity.DATA_stateSneaking, false);
	}
}
