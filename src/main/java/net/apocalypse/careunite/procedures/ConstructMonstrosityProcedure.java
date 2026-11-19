package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class ConstructMonstrosityProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		boolean hor = false;
		boolean vec = false;
		BlockState taregt = Blocks.AIR.defaultBlockState();
		double direc = 0;
		taregt = CaerulaArborModBlocks.IBERIA_MACHINE_BLOCK.get().defaultBlockState();
		if ((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == taregt.getBlock() && (world.getBlockState(BlockPos.containing(x, y - 2, z))).getBlock() == taregt.getBlock()) {
			vec = true;
		}
		if ((world.getBlockState(BlockPos.containing(x - 1, y - 1, z))).getBlock() == taregt.getBlock() && (world.getBlockState(BlockPos.containing(x - 1, y - 2, z))).getBlock() == taregt.getBlock()
				&& (world.getBlockState(BlockPos.containing(x + 1, y - 1, z))).getBlock() == taregt.getBlock() && (world.getBlockState(BlockPos.containing(x + 1, y - 2, z))).getBlock() == taregt.getBlock()) {
			hor = true;
			direc = 0;
		} else if ((world.getBlockState(BlockPos.containing(x, y - 1, z - 1))).getBlock() == taregt.getBlock() && (world.getBlockState(BlockPos.containing(x, y - 2, z - 1))).getBlock() == taregt.getBlock()
				&& (world.getBlockState(BlockPos.containing(x, y - 1, z + 1))).getBlock() == taregt.getBlock() && (world.getBlockState(BlockPos.containing(x, y - 2, z + 1))).getBlock() == taregt.getBlock()) {
			hor = true;
			direc = 1;
		}
		if (hor && vec) {
			world.destroyBlock(BlockPos.containing(x, y, z), false);
			world.destroyBlock(BlockPos.containing(x, y - 1, z), false);
			world.destroyBlock(BlockPos.containing(x, y - 2, z), false);
			if (direc == 0) {
				world.destroyBlock(BlockPos.containing(x - 1, y - 1, z), false);
				world.destroyBlock(BlockPos.containing(x - 1, y - 2, z), false);
				world.destroyBlock(BlockPos.containing(x + 1, y - 1, z), false);
				world.destroyBlock(BlockPos.containing(x + 1, y - 2, z), false);
			} else if (direc == 1) {
				world.destroyBlock(BlockPos.containing(x, y - 1, z - 1), false);
				world.destroyBlock(BlockPos.containing(x, y - 2, z - 1), false);
				world.destroyBlock(BlockPos.containing(x, y - 1, z + 1), false);
				world.destroyBlock(BlockPos.containing(x, y - 2, z + 1), false);
			}
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.IBERIA_MONSTROSITY.get().spawn(_level, BlockPos.containing(x, y - 2, z), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
			return;
		}
	}
}
