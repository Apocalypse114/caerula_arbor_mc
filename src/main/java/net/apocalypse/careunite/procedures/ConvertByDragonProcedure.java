package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class ConvertByDragonProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		BlockState target = Blocks.AIR.defaultBlockState();
		boolean shouldConvert = false;
		if (Math.random() < 0.5) {
			target = (world.getBlockState(BlockPos.containing(x, y + 1, z)));
			if (target.getBlock() == Blocks.DRAGON_EGG || (ForgeRegistries.BLOCKS.getKey(target.getBlock()).toString()).equals("kaleidoscope_end:suspicious_dragon_egg")) {
				shouldConvert = true;
			} else {
				for (Direction directioniterator : Direction.Plane.HORIZONTAL) {
					target = (world.getBlockState(BlockPos.containing(x + directioniterator.getStepX(), y, z + directioniterator.getStepZ())));
					if (target.getBlock() == Blocks.DRAGON_EGG || (ForgeRegistries.BLOCKS.getKey(target.getBlock()).toString()).equals("kaleidoscope_end:suspicious_dragon_egg")) {
						shouldConvert = true;
						break;
					}
					target = (world.getBlockState(BlockPos.containing(x + directioniterator.getStepX(), y + 1, z + directioniterator.getStepZ())));
					if (target.getBlock() == Blocks.DRAGON_EGG || (ForgeRegistries.BLOCKS.getKey(target.getBlock()).toString()).equals("kaleidoscope_end:suspicious_dragon_egg")) {
						shouldConvert = true;
						break;
					}
				}
			}
			if (shouldConvert) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.end_portal_frame.fill")), SoundSource.BLOCKS, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.end_portal_frame.fill")), SoundSource.BLOCKS, 1, 1, false);
					}
				}
				world.setBlock(BlockPos.containing(x, y, z), CaerulaArborModBlocks.DRAGON_BRAND.get().defaultBlockState(), 3);
			} else {
				target = (world.getBlockState(BlockPos.containing(x, y + 1, z)));
				if (target.getBlock() == Blocks.SNIFFER_EGG) {
					world.destroyBlock(BlockPos.containing(x, y + 1, z), false);
					world.destroyBlock(BlockPos.containing(x, y, z), false);
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = CaerulaArborModEntities.OCEANIZED_SNIFFER.get().spawn(_level, BlockPos.containing(x, y + 1, z), MobSpawnType.MOB_SUMMONED);
						if (entityToSpawn != null) {
							entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						}
					}
				}
			}
		}
	}
}
