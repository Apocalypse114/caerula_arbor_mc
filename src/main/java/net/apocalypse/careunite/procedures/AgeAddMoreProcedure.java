package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class AgeAddMoreProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		double expand = 0;
		if ((blockstate.getBlock().getStateDefinition().getProperty("grow_age") instanceof IntegerProperty _getip1 ? blockstate.getValue(_getip1) : -1) < 1) {
			{
				int _value = 1;
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("grow_age") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
		} else if ((blockstate.getBlock().getStateDefinition().getProperty("grow_age") instanceof IntegerProperty _getip4 ? blockstate.getValue(_getip4) : -1) == 2) {
			{
				int _value = 3;
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("grow_age") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
		}
	}
}
