package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.RaidSignEntity;

public class EraceRaidSignProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof RaidSignEntity) {
			if (!entity.level().isClientSide())
				entity.discard();
		}
	}
}
