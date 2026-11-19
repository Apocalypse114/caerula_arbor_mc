package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

public class GetPointSublimationProcedure {
	public static String execute(LevelAccessor world) {
		double rate = 0;
		if (CaerulaArborModVariables.MapVariables.get(world).strategy_sublimation >= 4) {
			return "\u00A7dFinished";
		}
		return Math.round(CaerulaArborModVariables.MapVariables.get(world).evo_point_sublimation) + "\u00A7d/"
				+ Math.round(12 * Math.pow(CaerulaArborModVariables.MapVariables.get(world).strategy_sublimation + 1, 3) * (double) CaerulaConfigsConfiguration.COEFFICIENT.get());
	}
}
