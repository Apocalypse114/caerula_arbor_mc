package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.PromotedAxolotlEntity;

public class RewardAxolotlHealProcedure {
	public static void execute(Entity sourceentity) {
		if (sourceentity == null)
			return;
		if (sourceentity instanceof PromotedAxolotlEntity _datEntSetI)
			_datEntSetI.getEntityData().set(PromotedAxolotlEntity.DATA_HEAL_PER_S, (int) Math.min((sourceentity instanceof PromotedAxolotlEntity _datEntI ? _datEntI.getEntityData().get(PromotedAxolotlEntity.DATA_HEAL_PER_S) : 0) + 2, 40));
	}
}
