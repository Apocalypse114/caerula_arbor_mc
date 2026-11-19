package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

public class FindValidYForCatProcedure {
	public static double execute(LevelAccessor world, double x, double y, double z, double xx, double yy, double zz) {
		double y_found = 0;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.azalea.hit")), SoundSource.NEUTRAL, 0, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.azalea.hit")), SoundSource.NEUTRAL, 0, 1, false);
			}
		}
		y_found = yy;
		for (int index0 = 0; index0 < 12; index0++) {
			y_found = yy + index0;
			if (!(world.getBlockFloorHeight(BlockPos.containing(xx, y_found, zz)) > 0)) {
				return y_found;
			}
		}
		return 114514;
	}
}
