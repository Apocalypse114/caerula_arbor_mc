package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.IsharmlaEntity;

public class IsIsharmlaMonsterProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return IsIsharmlaDurativeProcedure.execute(entity) && entity instanceof IsharmlaEntity _datEntL0 && _datEntL0.getEntityData().get(IsharmlaEntity.DATA_IS_MONSTER);
	}
}
