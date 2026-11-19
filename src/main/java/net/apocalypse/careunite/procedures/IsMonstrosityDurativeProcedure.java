package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.IberiaMonstrosityEntity;

public class IsMonstrosityDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof IberiaMonstrosityEntity _datEntI ? _datEntI.getEntityData().get(IberiaMonstrosityEntity.DATA_DURATION) : 0) <= 0;
	}
}
