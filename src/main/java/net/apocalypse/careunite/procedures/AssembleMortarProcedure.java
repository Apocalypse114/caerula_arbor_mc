package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class AssembleMortarProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z) {
		boolean vvv = false;
		boolean hhh = false;
		vvv = (world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == CaerulaArborModBlocks.MORTAR_MAIN_BLOCK.get() && (world.getBlockState(BlockPos.containing(x, y + 2, z))).getBlock() == CaerulaArborModBlocks.MORTAR_MAIN_BLOCK.get();
		hhh = true;
		for (int index0 = 0; index0 < 3; index0++) {
			for (int index1 = 0; index1 < 3; index1++) {
				if (!((world.getBlockState(BlockPos.containing(x + index0 - 1, y - 1, z + index1 - 1))).getBlock() == CaerulaArborModBlocks.MOTAR_BASE_BLOCK.get())) {
					hhh = false;
					break;
				}
			}
		}
		if (vvv && hhh) {
			world.destroyBlock(BlockPos.containing(x, y, z), false);
			world.destroyBlock(BlockPos.containing(x, y + 1, z), false);
			world.destroyBlock(BlockPos.containing(x, y + 2, z), false);
			for (int index2 = 0; index2 < 3; index2++) {
				for (int index3 = 0; index3 < 3; index3++) {
					world.destroyBlock(BlockPos.containing(x + index2 - 1, y - 1, z + index3 - 1), false);
				}
			}
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.AEGIR_MORTAR.get().spawn(_level, BlockPos.containing(x, y - 1, z), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
