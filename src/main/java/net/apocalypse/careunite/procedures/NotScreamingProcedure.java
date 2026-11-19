package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.ScreamChestFishEntity;

public class NotScreamingProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return !entity.isShiftKeyDown() && (entity instanceof ScreamChestFishEntity _datEntI ? _datEntI.getEntityData().get(ScreamChestFishEntity.DATA_SCREAM_TICK) : 0) <= 0;
	}
}
