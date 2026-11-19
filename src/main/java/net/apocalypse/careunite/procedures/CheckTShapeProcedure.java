package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class CheckTShapeProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z, BlockState tgt) {
		BlockState taregt = Blocks.AIR.defaultBlockState();
		double direc = 0;
		boolean vec = false;
		boolean hor = false;
		taregt = tgt;
		if ((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == taregt.getBlock() && (world.getBlockState(BlockPos.containing(x, y - 2, z))).getBlock() == taregt.getBlock()) {
			vec = true;
		}
		if ((world.getBlockState(BlockPos.containing(x - 1, y - 1, z))).getBlock() == taregt.getBlock() && (world.getBlockState(BlockPos.containing(x + 1, y - 1, z))).getBlock() == taregt.getBlock()
				&& (world.getBlockState(BlockPos.containing(x - 1, y, z))).getBlock() == Blocks.WITHER_SKELETON_SKULL && (world.getBlockState(BlockPos.containing(x + 1, y, z))).getBlock() == Blocks.WITHER_SKELETON_SKULL) {
			hor = true;
			direc = 0;
		} else if ((world.getBlockState(BlockPos.containing(x, y - 1, z - 1))).getBlock() == taregt.getBlock() && (world.getBlockState(BlockPos.containing(x, y - 1, z + 1))).getBlock() == taregt.getBlock()
				&& (world.getBlockState(BlockPos.containing(x, y, z - 1))).getBlock() == Blocks.WITHER_SKELETON_SKULL && (world.getBlockState(BlockPos.containing(x, y, z + 1))).getBlock() == Blocks.WITHER_SKELETON_SKULL) {
			hor = true;
			direc = 1;
		}
		if (hor && vec) {
			world.destroyBlock(BlockPos.containing(x, y, z), false);
			world.destroyBlock(BlockPos.containing(x, y - 1, z), false);
			world.destroyBlock(BlockPos.containing(x, y - 2, z), false);
			if (direc == 0) {
				world.destroyBlock(BlockPos.containing(x - 1, y, z), false);
				world.destroyBlock(BlockPos.containing(x + 1, y, z), false);
				world.destroyBlock(BlockPos.containing(x - 1, y - 1, z), false);
				world.destroyBlock(BlockPos.containing(x + 1, y - 1, z), false);
			} else if (direc == 1) {
				world.destroyBlock(BlockPos.containing(x, y, z - 1), false);
				world.destroyBlock(BlockPos.containing(x, y, z + 1), false);
				world.destroyBlock(BlockPos.containing(x, y - 1, z - 1), false);
				world.destroyBlock(BlockPos.containing(x, y - 1, z + 1), false);
			}
			return true;
		}
		return false;
	}
}
