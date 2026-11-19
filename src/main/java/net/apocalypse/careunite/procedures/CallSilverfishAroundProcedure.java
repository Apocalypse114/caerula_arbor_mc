package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class CallSilverfishAroundProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		boolean once = false;
		double hardness = 0;
		double dx = 0;
		double dy = 0;
		double dz = 0;
		double limithard = 0;
		if (world.isClientSide()) {
			return;
		}
		if (ModGriefSettingsProcedure.execute(world)) {
			if (Math.random() < 0.5) {
				dx = -5;
				for (int index0 = 0; index0 < 11; index0++) {
					dz = -5;
					for (int index1 = 0; index1 < 11; index1++) {
						dy = -3;
						for (int index2 = 0; index2 < 7; index2++) {
							if (Math.random() < 0.64) {
								if ((world.getBlockState(BlockPos.containing(x + dx, y + dy, z + dz))).getBlock() == CaerulaArborModBlocks.NETHERSEA_BUGGED_STONE.get()) {
									world.destroyBlock(BlockPos.containing(x + dx, y + dy, z + dz), false);
									if (world instanceof ServerLevel _level) {
										Entity entityToSpawn = CaerulaArborModEntities.OCEANIZED_SILVERFISH.get().spawn(_level, BlockPos.containing(x + dx, y + dy, z + dz), MobSpawnType.MOB_SUMMONED);
										if (entityToSpawn != null) {
											entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
										}
									}
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
}
