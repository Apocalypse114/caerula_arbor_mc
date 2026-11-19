package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class AegirGlassBarStyleProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		boolean upper = false;
		boolean lower = false;
		BlockState upperBlock = Blocks.AIR.defaultBlockState();
		BlockState lowerBlock = Blocks.AIR.defaultBlockState();
		upperBlock = (world.getBlockState(BlockPos.containing(x, y + 1, z)));
		lowerBlock = (world.getBlockState(BlockPos.containing(x, y - 1, z)));
		upper = upperBlock.getBlock() == CaerulaArborModBlocks.AEGIR_GLASS_BAR.get() || upperBlock.getBlock() == CaerulaArborModBlocks.AEGIR_GLASS_ARCH.get();
		lower = lowerBlock.getBlock() == CaerulaArborModBlocks.AEGIR_GLASS_BAR.get();
		if (upper && lower) {
			{
				int _value = 3;
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
		} else if (upper) {
			{
				int _value = 1;
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
		} else if (lower) {
			{
				int _value = 2;
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
		} else {
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
