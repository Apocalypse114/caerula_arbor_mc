package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.apocalypse.careunite.entity.OceanizedWanderingTraderEntity;

public class GetmyNearestLlamaProcedure {
	public static Entity execute(Entity entity) {
		if (entity instanceof OceanizedWanderingTraderEntity trd)
			return trd.getLlama();
		return null;
	}
}
