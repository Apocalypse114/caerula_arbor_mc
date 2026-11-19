package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.ShieldOfHonourEntity;

public class ShieldHonourTickProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double ddd = 0;
		ddd = entity instanceof ShieldOfHonourEntity _datEntI ? _datEntI.getEntityData().get(ShieldOfHonourEntity.DATA_DURA) : 0;
		if (ddd > 0) {
			if (entity instanceof ShieldOfHonourEntity _datEntSetI)
				_datEntSetI.getEntityData().set(ShieldOfHonourEntity.DATA_DURA, (int) (ddd - 1));
		}
	}
}
