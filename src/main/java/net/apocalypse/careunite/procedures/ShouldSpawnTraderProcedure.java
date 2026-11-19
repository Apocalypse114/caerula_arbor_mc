package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class ShouldSpawnTraderProcedure {
	public static boolean execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return false;
		boolean hasEcho = false;
		double echoCount = 0;
		echoCount = 0.5;
		for (int index0 = 0; index0 < 8; index0++) {
			if (GetEchoProcedure.execute(entity, index0) > 0) {
				hasEcho = true;
				echoCount = echoCount + 0.5;
			}
		}
		return hasEcho && Math.random() < CaerulaArborModVariables.MapVariables.get(world).traderSpawnRate * echoCount;
	}
}
