package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class FindGroundProcedure {
	public static double execute(LevelAccessor world, double xx, double yy, double zz) {
		BlockState target = Blocks.AIR.defaultBlockState();
		double findY = 0;
		findY = 114514;
		for (int dy = (int) 0; dy <= (int) 3; dy++) {
			target = (world.getBlockState(BlockPos.containing(xx, yy + dy, zz)));
			if (target.canBeReplaced() && world.getBlockFloorHeight(BlockPos.containing(xx, yy + dy - 1, zz)) > 0) {
				findY = yy + dy;
				break;
			}
			target = (world.getBlockState(BlockPos.containing(xx, yy - dy, zz)));
			if (target.canBeReplaced() && world.getBlockFloorHeight(BlockPos.containing(xx, yy - dy - 1, zz)) > 0) {
				findY = yy - dy;
				break;
			}
		}
		return findY;
	}
}
