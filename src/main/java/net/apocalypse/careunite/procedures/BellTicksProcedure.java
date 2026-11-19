package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.ShakingBellEntity;

public class BellTicksProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double atkp = 0;
		atkp = entity instanceof ShakingBellEntity _datEntI ? _datEntI.getEntityData().get(ShakingBellEntity.DATA_ATTACK_DURATION) : 0;
		if (atkp > 0) {
			if (entity instanceof ShakingBellEntity _datEntSetI)
				_datEntSetI.getEntityData().set(ShakingBellEntity.DATA_ATTACK_DURATION, (int) (atkp - 1));
		}
	}
}
