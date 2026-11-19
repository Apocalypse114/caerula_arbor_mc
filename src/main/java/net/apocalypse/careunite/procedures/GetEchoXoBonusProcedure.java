package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class GetEchoXoBonusProcedure {
	public static double execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return 0;
		double perc = 0;
		double evo = 0;
		if (GetEchoRemouldProcedure.execute(entity) != 0) {
			evo = CaerulaArborModVariables.MapVariables.get(world).strategy_breed;
			if (evo >= 3) {
				return 1.65;
			} else {
				return 1.5;
			}
		}
		return 1;
	}
}
