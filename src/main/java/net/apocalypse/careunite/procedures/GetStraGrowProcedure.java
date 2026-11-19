package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class GetStraGrowProcedure {
	public static double execute(LevelAccessor world) {
		return CaerulaArborModVariables.MapVariables.get(world).strategy_grow;
	}
}
