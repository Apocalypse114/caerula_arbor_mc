package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class CanDropTrailProcedure {
	public static boolean execute(LevelAccessor world, double xx, double yy, double zz) {
		BlockState targetBlock = Blocks.AIR.defaultBlockState();
		Direction dire = Direction.NORTH;
		double expand = 0;
		double longev = 0;
		boolean drop = false;
		drop = true;
		if (world.getBlockFloorHeight(BlockPos.containing(xx, yy, zz)) > 0) {
			return false;
		}
		for (int index0 = 0; index0 < 64; index0++) {
			targetBlock = (world.getBlockState(BlockPos.containing(xx, yy - index0 - 1, zz)));
			if (world.isEmptyBlock(BlockPos.containing(xx, yy - index0 - 1, zz)) || targetBlock.canBeReplaced()) {
				if (CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState().canSurvive(world, BlockPos.containing(xx, yy - index0 - 1, zz))) {
					return true;
				}
			} else {
				if (index0 > 0) {
					return false;
				}
				break;
			}
		}
		return false;
	}
}
