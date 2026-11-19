package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.CaerulaArborMod;

public class KillSelfProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity immediatesourceentity) {
		if (entity == null || immediatesourceentity == null)
			return;
		entity.invulnerableTime = 0;
		CaerulaArborMod.queueServerWork(2, () -> {
			if (!immediatesourceentity.level().isClientSide())
				immediatesourceentity.discard();
		});
	}
}
