package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedFoxEntity;

public class FoxHurtProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		entity.setShiftKeyDown(false);
		if (entity instanceof OceanizedFoxEntity _datEntSetL)
			_datEntSetL.getEntityData().set(OceanizedFoxEntity.DATA_sleeping, false);
		return true;
	}
}
