package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedLlamaEntity;

public class LlamaSKillingProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return (entity instanceof OceanizedLlamaEntity _datEntI ? _datEntI.getEntityData().get(OceanizedLlamaEntity.DATA_POUR_P) : 0) <= 0;
	}
}
