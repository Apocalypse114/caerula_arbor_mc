package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.apocalypse.careunite.entity.BaselayerAbyssalEntity;

public class LayTrailProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (CaerulaArborModBlocks.SEA_TRAIL_GROWN.get().defaultBlockState().canSurvive(world, BlockPos.containing(x, y, z)) && !(world.getBlockFloorHeight(BlockPos.containing(x, y, z)) > 0)) {
			if (ModGriefSettingsProcedure.execute(world)) {
				if ((entity instanceof BaselayerAbyssalEntity _datEntI ? _datEntI.getEntityData().get(BaselayerAbyssalEntity.DATA_mute_time) : 0) <= 0) {
					TrailReplaceProcedure.execute(world, CaerulaArborModBlocks.SEA_TRAIL_GROWN.get().defaultBlockState(), (world.getFluidState(BlockPos.containing(x, y, z)).createLegacyBlock()).getBlock() == Blocks.WATER, x, y, z);
				}
			}
		}
		AbyssalDropTombProcedure.execute(world, x, y, z, entity);
	}
}
