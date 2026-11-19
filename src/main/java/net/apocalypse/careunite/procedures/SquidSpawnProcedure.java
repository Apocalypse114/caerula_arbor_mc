package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class SquidSpawnProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		return UnderwaterSeabornSpawnProcedure.execute(world, x, y, z) && Math.min(CaerulaArborModVariables.MapVariables.get(world).strategy_grow, CaerulaArborModVariables.MapVariables.get(world).strategy_migration) >= 1;
	}
}
