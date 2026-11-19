package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class OnFarmLandProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		return (world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == CaerulaArborModBlocks.OCEAN_FARMLAND.get();
	}
}
