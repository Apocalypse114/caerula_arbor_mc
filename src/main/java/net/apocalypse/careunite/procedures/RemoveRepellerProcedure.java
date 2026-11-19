package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.TideDeathrepellerEntity;

public class RemoveRepellerProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof TideDeathrepellerEntity) {
			if (!entity.level().isClientSide())
				entity.discard();
		}
	}
}
