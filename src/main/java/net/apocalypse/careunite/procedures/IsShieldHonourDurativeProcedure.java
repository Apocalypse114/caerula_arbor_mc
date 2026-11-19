package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.ShieldOfHonourEntity;

public class IsShieldHonourDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		if (IsAlchemyStunnedProcedure.execute(entity)) {
			return false;
		}
		return (entity instanceof ShieldOfHonourEntity _datEntI ? _datEntI.getEntityData().get(ShieldOfHonourEntity.DATA_DURA) : 0) <= 0;
	}
}
