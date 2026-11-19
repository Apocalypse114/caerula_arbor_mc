package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedWanderingTraderEntity;

public class WanderingNotActioningProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof OceanizedWanderingTraderEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWanderingTraderEntity.DATA_ACTION_TICK) : 0) <= 0;
	}
}
