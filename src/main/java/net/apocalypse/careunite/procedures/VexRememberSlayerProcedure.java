package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedVexEntity;

public class VexRememberSlayerProcedure {
	public static void execute(Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		entity.setDeltaMovement(new Vec3(0, 0, 0));
		if (entity instanceof OceanizedVexEntity _datEntSetS)
			_datEntSetS.getEntityData().set(OceanizedVexEntity.DATA_SAYER, (sourceentity.getStringUUID()));
	}
}
