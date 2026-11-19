package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.PromotedAxolotlEntity;

public class AxolotlFakingDeathProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return (entity instanceof PromotedAxolotlEntity _datEntI ? _datEntI.getEntityData().get(PromotedAxolotlEntity.DATA_FAKE_DEATH_T) : 0) <= 0;
	}
}
