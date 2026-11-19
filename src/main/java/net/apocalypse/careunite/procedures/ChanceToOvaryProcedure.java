package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.Difficulty;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModGameRules;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

public class ChanceToOvaryProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		double chance = 0;
		double rate = 0;
		if (!(world.getDifficulty() == Difficulty.PEACEFUL)) {
			if (!(world.getBlockFloorHeight(BlockPos.containing(x, y + 1, z)) > 0) && !(world.getBlockFloorHeight(BlockPos.containing(x, y + 2, z)) > 0)) {
				if ((blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip4 ? blockstate.getValue(_getip4) : -1) == 0) {
					rate = 0.05;
					if (CaerulaArborModVariables.MapVariables.get(world).strategy_breed >= 2) {
						rate = 0.08;
					}
					if (CaerulaArborModVariables.MapVariables.get(world).strategy_breed >= 4) {
						rate = 0.1;
					}
					if (GetSeabornNumProcedure.execute(world, x, y, z) >= Math.min((double) CaerulaConfigsConfiguration.CLONE_NUM.get(), (world.getLevelData().getGameRules().getInt(CaerulaArborModGameRules.CLONE_NUMBER_LIMIT)))) {
						return;
					}
					for (int index0 = 0; index0 < (int) GetMultilFronEvolutionProcedure.execute(world); index0++) {
						SummonRandomSeabornProcedure.execute(world, rate, x + 0.5, y + 1.5, z + 0.5);
					}
					if (!(blockstate.getBlock().getStateDefinition().getProperty("powered") instanceof BooleanProperty _getbp8 && blockstate.getValue(_getbp8))) {
						{
							int _value = 1;
							BlockPos _pos = BlockPos.containing(x, y, z);
							BlockState _bs = world.getBlockState(_pos);
							if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
								world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
						}
					}
				}
				if (blockstate.getBlock().getStateDefinition().getProperty("powered") instanceof BooleanProperty _getbp11 && blockstate.getValue(_getbp11)) {
					{
						int _value = 0;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
				}
			}
		}
	}
}
