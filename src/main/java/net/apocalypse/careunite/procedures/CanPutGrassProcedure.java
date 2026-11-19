package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class CanPutGrassProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		return (world.getBlockState(BlockPos.containing(x, y - 1, z)).isFaceSturdy(world, BlockPos.containing(x, y - 1, z), Direction.UP)
				|| (world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == CaerulaArborModBlocks.DEEP_SEAGRASS.get())
				&& !((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == CaerulaArborModBlocks.DEEP_SEAGRASS.get() && (world.getBlockState(BlockPos.containing(x, y - 2, z))).getBlock() == CaerulaArborModBlocks.DEEP_SEAGRASS.get());
	}
}
