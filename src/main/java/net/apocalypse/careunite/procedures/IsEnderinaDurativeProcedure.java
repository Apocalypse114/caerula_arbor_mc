package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedEnderinaEntity;
import net.minecraft.network.syncher.SynchedEntityData;

public class IsEnderinaDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		if (entity instanceof OceanizedEnderinaEntity _datEntI){
			SynchedEntityData data = _datEntI.getEntityData();
			return data.get(OceanizedEnderinaEntity.DATA_DURATION) <= 0
				&& data.get(OceanizedEnderinaEntity.DATA_REVIVE_TICK) <= 0;
		}
		return false;
	}
}
