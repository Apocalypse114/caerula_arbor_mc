package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.OceanizedSnifferEggEntity;

public class InitSnifferEggProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof OceanizedSnifferEggEntity _datEntSetI)
			_datEntSetI.getEntityData().set(OceanizedSnifferEggEntity.DATA_HATCH_TIME, Mth.nextInt(RandomSource.create(), 100, 240));
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sniffer_egg.plop")), SoundSource.HOSTILE, 2, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sniffer_egg.plop")), SoundSource.HOSTILE, 2, 1, false);
			}
		}
	}
}
