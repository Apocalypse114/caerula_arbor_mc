package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.CreeperPetEntity;

public class CreeperStandUpProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof CreeperPetEntity _datEntSetL)
			_datEntSetL.getEntityData().set(CreeperPetEntity.DATA_SIT, false);
	}
}
