package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class MonstrosityDestoryBlockProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double dx = 0;
		double dy = 0;
		double dz = 0;
		double hardness = 0;
		double lose = 0;
		BlockState block = Blocks.AIR.defaultBlockState();
		if (ModGriefSettingsProcedure.execute(world)) {
			dx = -1;
			for (int index0 = 0; index0 < 3; index0++) {
				dz = -1;
				for (int index1 = 0; index1 < 3; index1++) {
					dy = 0;
					for (int index2 = 0; index2 < 5; index2++) {
						block = (world.getBlockState(BlockPos.containing(x + dx, y + dy, z + dz)));
						hardness = block.getDestroySpeed(world, BlockPos.containing(0, 0, 0));
						if (hardness == 0 && world.getBlockFloorHeight(BlockPos.containing(x + dx, y + dy, z + dz)) > 0) {
							{
								BlockPos _pos = BlockPos.containing(x + dx, y + dy, z + dz);
								Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x, y, z), null);
								world.destroyBlock(_pos, false);
							}
						}
						dy = dy + 1;
					}
					dz = dz + 1;
				}
				dx = dx + 1;
			}
		}
	}
}
