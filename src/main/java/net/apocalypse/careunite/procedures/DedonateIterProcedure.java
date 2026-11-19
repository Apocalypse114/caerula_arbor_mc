package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.apocalypse.careunite.CaerulaArborMod;

public class DedonateIterProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z, boolean burn, double iter) {
		BlockState target = Blocks.AIR.defaultBlockState();
		boolean worked = false;
		target = (world.getBlockState(BlockPos.containing(x, y, z)));
		if (target.getBlock() == CaerulaArborModBlocks.OCEAN_OVARY.get() || target.getBlock() == CaerulaArborModBlocks.RED_OVARY.get()) {
			{
				BlockPos _pos = BlockPos.containing(x, y, z);
				Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x + 0.5, y, z + 0.5), null);
				world.destroyBlock(_pos, false);
			}
			worked = true;
		} else if (target.getBlock() == CaerulaArborModBlocks.NETHERSEA_WOOD.get() || target.getBlock() == CaerulaArborModBlocks.STRIPPED_NETHERSEA_WOOD.get() || target.getBlock() == CaerulaArborModBlocks.TRAIL_LOG.get()
				|| target.getBlock() == CaerulaArborModBlocks.STRIPPED_TRAIL_LOG.get()) {
			world.destroyBlock(BlockPos.containing(x, y, z), false);
			worked = true;
		} else {
			if (burn && BurndownTrailProcedure.execute(world, target, x, y, z)) {
				worked = true;
			} else {
				if (target.is(BlockTags.create(new ResourceLocation("caerula_arbor_reunite:blow_up")))) {
					world.destroyBlock(BlockPos.containing(x, y, z), false);
					worked = true;
				}
			}
		}
		if (worked && iter > 0) {
			CaerulaArborMod.queueServerWork(1, () -> {
				DedonateDelayProcedure.execute(world, x, y, z, burn, iter);
			});
		}
		return worked;
	}
}
