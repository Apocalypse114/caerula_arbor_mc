package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class SnifferDigParticleProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		BlockState bst = Blocks.AIR.defaultBlockState();
		for (int index0 = 0; index0 < 3; index0++) {
			for (int index1 = 0; index1 < 3; index1++) {
				bst = (world.getBlockState(BlockPos.containing(x + index0 - 1, y - 1, z + index1 - 1)));
				world.levelEvent(2001, BlockPos.containing(x + index0 - 1, y, z + index1 - 1), Block.getId(bst));
			}
		}
	}
}
