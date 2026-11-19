package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class CanPutTrailProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		return (world.getBlockState(BlockPos.containing(x, y - 1, z)).isFaceSturdy(world, BlockPos.containing(x, y - 1, z), Direction.UP)
				|| (world.getBlockState(BlockPos.containing(x, y - 1, z))).is(BlockTags.create(new ResourceLocation("caerula_arbor_reunite:trail_existable"))))
				&& !((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == CaerulaArborModBlocks.SEA_TRAIL_SOLID.get());
	}
}
