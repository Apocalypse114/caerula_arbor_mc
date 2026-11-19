package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.SkadiCorruptedEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class CorruptedDoHurtTargetProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		CaerulaArborMod.queueServerWork(24, () -> {
			if (sourceentity.isAlive() && IsCorruptedDurativeProcedure.execute(sourceentity)) {
				if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 2.25) {
					CorruptedRetreatProcedure.execute(world, sourceentity);
					if (sourceentity instanceof SkadiCorruptedEntity _datEntSetI)
						_datEntSetI.getEntityData().set(SkadiCorruptedEntity.DATA_duration, 40);
				}
			}
		});
	}
}
