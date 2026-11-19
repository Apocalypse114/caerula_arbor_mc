package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceannizedWitheriaEntity;
import net.apocalypse.careunite.entity.OceanizedWitherEntity;

public class IsWitherDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof OceanizedWitherEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWitherEntity.DATA_duration) : 0) <= 0
				&& (entity instanceof OceannizedWitheriaEntity _datEntI ? _datEntI.getEntityData().get(OceannizedWitheriaEntity.DATA_duration) : 0) <= 0;
	}
}
