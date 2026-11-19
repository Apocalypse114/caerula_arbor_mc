package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.IzumikEntity;

public class PhasAfter0Procedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return (entity instanceof IzumikEntity _datEntI ? _datEntI.getEntityData().get(IzumikEntity.DATA_phase) : 0) > 0 && IsNotFakeDyingProcedure.execute(entity);
	}
}
