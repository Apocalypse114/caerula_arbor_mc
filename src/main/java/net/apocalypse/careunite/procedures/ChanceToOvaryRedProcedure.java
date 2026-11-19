package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.Difficulty;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModGameRules;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

public class ChanceToOvaryRedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double chance = 0;
		double rate = 0;
		if (!(world.getDifficulty() == Difficulty.PEACEFUL)) {
			if (!(world.getBlockFloorHeight(BlockPos.containing(x, y + 1, z)) > 0) && !(world.getBlockFloorHeight(BlockPos.containing(x, y + 2, z)) > 0)) {
				rate = 0.5;
				if (CaerulaArborModVariables.MapVariables.get(world).strategy_breed >= 2) {
					rate = 0.65;
				}
				if (CaerulaArborModVariables.MapVariables.get(world).strategy_breed >= 4) {
					rate = 0.7;
				}
				if (GetSeabornNumProcedure.execute(world, x, y, z) < Math.min((world.getLevelData().getGameRules().getInt(CaerulaArborModGameRules.CLONE_NUMBER_LIMIT)), (double) CaerulaConfigsConfiguration.CLONE_NUM.get())) {
					for (int index0 = 0; index0 < (int) GetMultilFronEvolutionProcedure.execute(world); index0++) {
						SummonRandomSeabornProcedure.execute(world, rate, x + 0.5, y + 1.5, z + 0.5);
					}
				}
			}
		}
	}
}
