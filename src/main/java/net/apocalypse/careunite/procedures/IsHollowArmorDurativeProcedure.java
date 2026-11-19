package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.HollowArmorEntity;

public class IsHollowArmorDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		if (IsAlchemyStunnedProcedure.execute(entity)) {
			return false;
		}
		return (entity instanceof HollowArmorEntity _datEntI ? _datEntI.getEntityData().get(HollowArmorEntity.DATA_DURATION) : 0) <= 0;
	}
}
