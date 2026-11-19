package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.CaerulaArborMod;

public class FleeBulletHitProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
		if (entity == null || immediatesourceentity == null)
			return;
		if (!(entity == sourceentity)) {
			GiveLessArmorProcedure.execute(entity, 3);
			entity.invulnerableTime = 0;
		}
		CaerulaArborMod.queueServerWork(10, () -> {
			if (!immediatesourceentity.level().isClientSide())
				immediatesourceentity.discard();
		});
	}
}
