package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

public class WitheriaDestoryBlockProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		boolean once = false;
		double dx = 0;
		double dy = 0;
		double dz = 0;
		double hardness = 0;
		double lose = 0;
		BlockState block = Blocks.AIR.defaultBlockState();
		if (ModGriefSettingsProcedure.execute(world)) {
			once = false;
			dx = -1;
			for (int index0 = 0; index0 < 3; index0++) {
				dz = -1;
				for (int index1 = 0; index1 < 3; index1++) {
					dy = 0;
					for (int index2 = 0; index2 < 2; index2++) {
						block = (world.getBlockState(BlockPos.containing(x + dx, y + dy, z + dz)));
						if (!block.is(BlockTags.create(new ResourceLocation("minecraft:wither_immnue")))) {
							hardness = block.getDestroySpeed(world, BlockPos.containing(0, 0, 0));
							if (hardness <= 7.5 && hardness >= 0 && world.getBlockFloorHeight(BlockPos.containing(x + dx, y + dy, z + dz)) > 0) {
								{
									BlockPos _pos = BlockPos.containing(x + dx, y + dy, z + dz);
									Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x, y, z), null);
									world.destroyBlock(_pos, false);
								}
								if (world instanceof Level _level)
									_level.updateNeighborsAt(BlockPos.containing(x + dx, y + dy, z + dz), _level.getBlockState(BlockPos.containing(x + dx, y + dy, z + dz)).getBlock());
								once = true;
							}
						}
						dy = dy + 1;
					}
					dz = dz + 1;
				}
				dx = dx + 1;
			}
			if (once) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.break_block")), SoundSource.NEUTRAL, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.break_block")), SoundSource.NEUTRAL, 1, 1, false);
					}
				}
			}
		}
	}
}
