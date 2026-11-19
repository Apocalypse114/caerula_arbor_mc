package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.ApocataEntity;

public class ApocataDurationDeletionProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double dura = 0;
		dura = entity instanceof ApocataEntity _datEntI ? _datEntI.getEntityData().get(ApocataEntity.DATA_duration) : 0;
		if (dura > 0) {
			if (entity instanceof ApocataEntity _datEntSetI)
				_datEntSetI.getEntityData().set(ApocataEntity.DATA_duration, (int) (dura - 1));
		}
	}
}
