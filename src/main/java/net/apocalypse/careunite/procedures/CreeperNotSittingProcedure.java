package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.CreeperPetEntity;

public class CreeperNotSittingProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return !(entity instanceof CreeperPetEntity _datEntL0 && _datEntL0.getEntityData().get(CreeperPetEntity.DATA_SIT));
	}
}
