package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class AxolotlSpawnProcedure {
	public static boolean execute(LevelAccessor world) {
		return Math.min(CaerulaArborModVariables.MapVariables.get(world).strategy_grow, CaerulaArborModVariables.MapVariables.get(world).strategy_migration) >= 1;
	}
}
