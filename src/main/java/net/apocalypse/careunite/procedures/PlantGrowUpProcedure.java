package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class PlantGrowUpProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		BlockState farmland = Blocks.AIR.defaultBlockState();
		double age = 0;
		boolean shouldGrow = false;
		boolean moist = false;
		if (Math.random() < 0.4) {
			age = blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip1 ? blockstate.getValue(_getip1) : -1;
			if (age < 2) {
				farmland = (world.getBlockState(BlockPos.containing(x, y - 1, z)));
				if (farmland.getBlock() == CaerulaArborModBlocks.OCEAN_FARMLAND.get()) {
					moist = (farmland.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip4 ? farmland.getValue(_getip4) : -1) == 1;
					shouldGrow = moist || !moist && Math.random() < 0.5;
					if (shouldGrow) {
						{
							int _value = (int) (age + 1);
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
}
