package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class IreneBurnBrandAroundProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		BlockState toBeBurn = Blocks.AIR.defaultBlockState();
		double px = 0;
		double py = 0;
		double pz = 0;
		for (int index0 = 0; index0 < 3; index0++) {
			for (int index1 = 0; index1 < 3; index1++) {
				for (int index2 = 0; index2 < 3; index2++) {
					px = x + index0 - 1;
					py = y + index1 - 1;
					pz = z + index2 - 1;
					toBeBurn = (world.getBlockState(BlockPos.containing(px, py, pz)));
					if (toBeBurn.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_INIT.get() || toBeBurn.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_GROWING.get() || toBeBurn.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_GROWN.get()
							|| toBeBurn.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_STOP.get() || toBeBurn.getBlock() == CaerulaArborModBlocks.SEA_TRAIL_SOLID.get() || toBeBurn.getBlock() == CaerulaArborModBlocks.TRAIL_PULSE.get()) {
						BurndownTrailProcedure.execute(world, toBeBurn, px, py, pz);
						if (world instanceof ServerLevel _level)
							_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.PURPLE_FLAME.get()), (x + 0.5), (y + 1), (z + 0.5), 16, 0.75, 0.75, 0.75, 0.15);
					}
				}
			}
		}
	}
}
