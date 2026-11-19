package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedEnderinaEntity;

public class EnderinaReviveingProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof OceanizedEnderinaEntity _datEntSetI)
			_datEntSetI.getEntityData().set(OceanizedEnderinaEntity.DATA_DURATION, 600);
		if (entity instanceof OceanizedEnderinaEntity _datEntSetI)
			_datEntSetI.getEntityData().set(OceanizedEnderinaEntity.DATA_REVIVE_TICK, 600);
	}
}
