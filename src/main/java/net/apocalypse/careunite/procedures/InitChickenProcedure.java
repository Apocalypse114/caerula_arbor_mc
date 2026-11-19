package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

import net.apocalypse.careunite.entity.OceanizedChickenEntity;

public class InitChickenProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof OceanizedChickenEntity _datEntSetI)
			_datEntSetI.getEntityData().set(OceanizedChickenEntity.DATA_GROW_TIME, (int) (10000 - Mth.nextInt(RandomSource.create(), 0, 6000)));
		if (entity instanceof OceanizedChickenEntity _datEntSetI)
			_datEntSetI.getEntityData().set(OceanizedChickenEntity.DATA_LAY_COOLDOWN, (int) (1200 + Mth.nextInt(RandomSource.create(), -100, 100)));
	}
}
