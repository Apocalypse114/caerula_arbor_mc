package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class DetectMoistureProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		BlockState bbb = Blocks.AIR.defaultBlockState();
		boolean moist = false;
		double bs = 0;
		for (int index0 = 0; index0 < 9; index0++) {
			for (int index1 = 0; index1 < 9; index1++) {
				bbb = (world.getBlockState(BlockPos.containing(x + index0 - 4, y, z + index1 - 4)));
				if (bbb.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_SOLID.get() || bbb.getBlock() == CaerulaArborModBlocks.TRAIL_PULSE.get() || bbb.getBlock() == CaerulaArborModBlocks.NETHERSEA_SOUL_SAND.get()) {
					moist = true;
				}
			}
		}
		bs = blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip5 ? blockstate.getValue(_getip5) : -1;
		if (moist) {
			if (bs == 0) {
				{
					int _value = 1;
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
						world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
				}
			}
		} else {
			if (bs == 1) {
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
