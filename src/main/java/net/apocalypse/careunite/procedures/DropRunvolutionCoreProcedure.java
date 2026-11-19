package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class DropRunvolutionCoreProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		DedonateTrialProcedure.execute(world, x, y, z);
		world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
		if (world instanceof ServerLevel _level)
			FallingBlockEntity.fall(_level, BlockPos.containing(x, y + 3, z), CaerulaArborModBlocks.RUNVOLUTION_SPAWN_BLOCK.get().defaultBlockState());
	}
}
