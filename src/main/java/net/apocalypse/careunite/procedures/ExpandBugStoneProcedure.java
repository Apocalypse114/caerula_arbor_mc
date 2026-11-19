package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.util.RandomSource;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class ExpandBugStoneProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		BlockState target = Blocks.AIR.defaultBlockState();
		Direction ddd = Direction.NORTH;
		double durab = 0;
		durab = blockstate.getBlock().getStateDefinition().getProperty("durability") instanceof IntegerProperty _getip1 ? blockstate.getValue(_getip1) : -1;
		if (durab > 0) {
			ddd = Direction.getRandom(RandomSource.create());
			for (int index0 = 0; index0 < 16; index0++) {
				ddd = Direction.getRandom(RandomSource.create());
				target = (world.getBlockState(BlockPos.containing(x + ddd.getStepX(), y + ddd.getStepY(), z + ddd.getStepZ())));
				if (target.getBlock() == CaerulaArborModBlocks.TRAIL_STONE.get()) {
					break;
				}
			}
			if (target.getBlock() == CaerulaArborModBlocks.TRAIL_STONE.get()) {
				{
					int _value = (int) (durab - 1);
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("durability") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
						world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
				}
				world.setBlock(BlockPos.containing(x + ddd.getStepX(), y + ddd.getStepY(), z + ddd.getStepZ()), (new Object() {
					public BlockState with(BlockState _bs, String _property, int _newValue) {
						Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty(_property);
						return _prop instanceof IntegerProperty _ip && _prop.getPossibleValues().contains(_newValue) ? _bs.setValue(_ip, _newValue) : _bs;
					}
				}.with(CaerulaArborModBlocks.NETHERSEA_BUGGED_STONE.get().defaultBlockState(), "durability", (int) (durab - 1))), 3);
			}
		}
	}
}
