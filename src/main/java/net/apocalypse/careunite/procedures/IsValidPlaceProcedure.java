package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

public class IsValidPlaceProcedure {
	public static boolean execute(LevelAccessor world, double xx, double yy, double zz) {
		if (world instanceof Level _level) {
			if (_level.isClientSide()) {
				_level.playLocalSound(xx, yy, zz, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.ambient")), SoundSource.HOSTILE, 0, 1, false);
			}
		}
		for (int dy = (int) 0; dy <= (int) 3; dy++) {
			if (world.getBlockFloorHeight(BlockPos.containing(xx, yy + dy, zz)) > 0) {
				return false;
			}
		}
		return true;
	}
}
