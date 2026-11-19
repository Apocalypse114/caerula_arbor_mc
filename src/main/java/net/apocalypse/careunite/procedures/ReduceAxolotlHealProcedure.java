package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.PromotedAxolotlEntity;

public class ReduceAxolotlHealProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof PromotedAxolotlEntity _datEntSetI)
			_datEntSetI.getEntityData().set(PromotedAxolotlEntity.DATA_HEAL_PER_S, (int) Math.max((entity instanceof PromotedAxolotlEntity _datEntI ? _datEntI.getEntityData().get(PromotedAxolotlEntity.DATA_HEAL_PER_S) : 0) - 1, 1));
	}
}
