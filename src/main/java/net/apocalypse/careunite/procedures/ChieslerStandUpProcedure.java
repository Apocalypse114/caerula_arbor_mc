package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.ChieslerPetEntity;

public class ChieslerStandUpProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof ChieslerPetEntity _datEntSetL)
			_datEntSetL.getEntityData().set(ChieslerPetEntity.DATA_SITTING, false);
	}
}
