package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class GetMultilFronEvolutionProcedure {
	public static double execute(LevelAccessor world) {
		double ev = 0;
		double r = 0;
		double r1 = 0;
		ev = CaerulaArborModVariables.MapVariables.get(world).evo_point_breed;
		if (ev <= 0) {
			return 1;
		}
		r = 0.05 * (ev + 1);
		if (ev <= 2) {
			if (Math.random() < r) {
				return 2;
			}
			return 1;
		}
		r1 = 0.05 * (ev - 2);
		if (Math.random() < r) {
			if (Math.random() < r) {
				return 3;
			}
			return 2;
		}
		return 1;
	}
}
