package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.SkadiCorruptedEntity;

public class PurifySkadiCorruptedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof SkadiCorruptedEntity _datEntSetI)
			_datEntSetI.getEntityData().set(SkadiCorruptedEntity.DATA_deal, 999999);
	}
}
