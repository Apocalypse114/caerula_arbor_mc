package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.RaidSignEntity;

public class RaiderFromSignProcedure {
	public static Entity execute(Entity entity) {
		if (entity == null)
			return null;
		if (entity instanceof RaidSignEntity sign) {
			return sign.getARaider();
		}
		return null;
	}
}
