package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.apocalypse.careunite.entity.OceanizedSpiderEntity;

public class LayOvaryProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (ModGriefSettingsProcedure.execute(world) && Math.random() < 0.33) {
			if ((world.getBlockState(BlockPos.containing(x, y, z))).canBeReplaced()) {
				if ((entity instanceof OceanizedSpiderEntity _datEntI ? _datEntI.getEntityData().get(OceanizedSpiderEntity.DATA_mute_time) : 0) <= 0) {
					if (Math.random() < 0.25) {
						world.levelEvent(2001, BlockPos.containing(x, y, z), Block.getId(CaerulaArborModBlocks.RED_OVARY.get().defaultBlockState()));
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_vein.place")), SoundSource.HOSTILE, 1, (float) 0.8);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_vein.place")), SoundSource.HOSTILE, 1, (float) 0.8, false);
							}
						}
						if (world instanceof ServerLevel _level)
							FallingBlockEntity.fall(_level, BlockPos.containing(x, y, z), CaerulaArborModBlocks.RED_OVARY.get().defaultBlockState());
					} else {
						world.levelEvent(2001, BlockPos.containing(x, y, z), Block.getId(CaerulaArborModBlocks.OCEAN_OVARY.get().defaultBlockState()));
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_vein.place")), SoundSource.HOSTILE, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_vein.place")), SoundSource.HOSTILE, 1, 1, false);
							}
						}
						if (world instanceof ServerLevel _level)
							FallingBlockEntity.fall(_level, BlockPos.containing(x, y, z), CaerulaArborModBlocks.OCEAN_OVARY.get().defaultBlockState());
					}
				}
			}
		}
	}
}
