package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class GetBlockUnderEntityProcedure {
	public static BlockState execute(LevelAccessor world, double x, double y, double z) {
		return world.getBlockState(BlockPos.containing(x, y - 0.5, z));
	}
}
