package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

import net.apocalypse.careunite.entity.OceanizedVexEntity;

public class InitOceanziedVexProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof OceanizedVexEntity _datEntSetI)
			_datEntSetI.getEntityData().set(OceanizedVexEntity.DATA_leftSurvivalTick, (int) (600 + Mth.nextInt(RandomSource.create(), 0, 1800)));
	}
}
