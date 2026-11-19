package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class DistFromGroundProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		double block = 0;
		if (y < -32) {
			return false;
		}
		for (int index0 = 0; index0 < 20; index0++) {
			if (!(world.isEmptyBlock(BlockPos.containing(x, y - index0 - 1, z)) || (world.getBlockState(BlockPos.containing(x, y - index0 - 1, z))).getBlock() instanceof LiquidBlock)) {
				return false;
			}
		}
		return true;
	}
}
