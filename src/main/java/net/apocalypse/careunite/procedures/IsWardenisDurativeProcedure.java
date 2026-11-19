package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedWardenisEntity;

public class IsWardenisDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return (entity instanceof OceanizedWardenisEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWardenisEntity.DATA_duration) : 0) <= 0;
	}
}
