package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.ReaperPetEntity;

public class PetGetupProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof ReaperPetEntity _datEntSetI)
			_datEntSetI.getEntityData().set(ReaperPetEntity.DATA_state, 0);
	}
}
