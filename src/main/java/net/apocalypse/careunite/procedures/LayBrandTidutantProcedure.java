package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.apocalypse.careunite.entity.TidutantExcrescenceEntity;

public class LayBrandTidutantProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (ModGriefSettingsProcedure.execute(world)) {
			if ((entity instanceof TidutantExcrescenceEntity _datEntI ? _datEntI.getEntityData().get(TidutantExcrescenceEntity.DATA_mute_time) : 0) <= 0) {
				if (CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState().canSurvive(world, BlockPos.containing(x, y, z)) && !(world.getBlockFloorHeight(BlockPos.containing(x, y, z)) > 0)) {
					TrailReplaceProcedure.execute(world, CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState(), (world.getFluidState(BlockPos.containing(x, y, z)).createLegacyBlock()).getBlock() == Blocks.WATER, x, y, z);
				}
				for (Direction directioniterator : Direction.Plane.HORIZONTAL) {
					if (CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState().canSurvive(world, BlockPos.containing(x + directioniterator.getStepX(), y, z + directioniterator.getStepZ()))
							&& !(world.getBlockFloorHeight(BlockPos.containing(x + directioniterator.getStepX(), y, z + directioniterator.getStepZ())) > 0)) {
						if (Math.random() < 0.25) {
							TrailReplaceProcedure.execute(world, CaerulaArborModBlocks.SEA_TRAIL_INIT.get().defaultBlockState(),
									(world.getFluidState(BlockPos.containing(x + directioniterator.getStepX(), y, z + directioniterator.getStepZ())).createLegacyBlock()).getBlock() == Blocks.WATER, x + directioniterator.getStepX(), y,
									z + directioniterator.getStepZ());
						}
					}
				}
			}
		}
	}
}
