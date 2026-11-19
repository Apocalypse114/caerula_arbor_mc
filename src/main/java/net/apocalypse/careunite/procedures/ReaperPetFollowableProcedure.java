package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.ReaperPetEntity;

public class ReaperPetFollowableProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return (entity instanceof ReaperPetEntity _datEntI ? _datEntI.getEntityData().get(ReaperPetEntity.DATA_state) : 0) == 0;
	}
}
