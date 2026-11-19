package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

public class OnNetherseaBrandProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		if ((world.getBlockState(BlockPos.containing(x, y, z))).is(BlockTags.create(new ResourceLocation("caerula_arbor_reunite:trail")))) {
			return true;
		}
		return (world.getBlockState(BlockPos.containing(x, y - 0.5, z))).is(BlockTags.create(new ResourceLocation("caerula_arbor_reunite:trail")));
	}
}
