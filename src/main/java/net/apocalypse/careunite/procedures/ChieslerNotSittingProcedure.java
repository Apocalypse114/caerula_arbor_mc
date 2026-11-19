package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.ChieslerPetEntity;

public class ChieslerNotSittingProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return !(entity instanceof ChieslerPetEntity _datEntL0 && _datEntL0.getEntityData().get(ChieslerPetEntity.DATA_SITTING));
	}
}
