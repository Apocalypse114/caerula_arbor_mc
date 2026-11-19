package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedDogEntity;

public class DogStandupProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof OceanizedDogEntity _datEntSetL)
			_datEntSetL.getEntityData().set(OceanizedDogEntity.DATA_sitting, false);
	}
}
