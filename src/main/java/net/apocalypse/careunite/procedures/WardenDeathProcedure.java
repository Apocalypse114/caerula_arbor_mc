package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.CaerulaArborMod;

public class WardenDeathProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		CaerulaArborMod.queueServerWork(10, () -> {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_charge")), SoundSource.HOSTILE, (float) 0.1, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_charge")), SoundSource.HOSTILE, (float) 0.1, 1, false);
				}
			}
			for (int index0 = 0; index0 < 3; index0++) {
				WardenRimedParticleProcedure.execute(world, x, y, z);
			}
		});
		CaerulaArborMod.queueServerWork(20, () -> {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_charge")), SoundSource.HOSTILE, (float) 0.2, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_charge")), SoundSource.HOSTILE, (float) 0.2, 1, false);
				}
			}
			for (int index1 = 0; index1 < 5; index1++) {
				WardenRimedParticleProcedure.execute(world, x, y, z);
			}
		});
		CaerulaArborMod.queueServerWork(32, () -> {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_charge")), SoundSource.HOSTILE, (float) 0.3, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_charge")), SoundSource.HOSTILE, (float) 0.3, 1, false);
				}
			}
			for (int index2 = 0; index2 < 9; index2++) {
				WardenRimedParticleProcedure.execute(world, x, y, z);
			}
		});
		CaerulaArborMod.queueServerWork(35, () -> {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_charge")), SoundSource.HOSTILE, (float) 0.4, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_charge")), SoundSource.HOSTILE, (float) 0.4, 1, false);
				}
			}
			for (int index3 = 0; index3 < 9; index3++) {
				WardenRimedParticleProcedure.execute(world, x, y, z);
			}
		});
		CaerulaArborMod.queueServerWork(38, () -> {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_charge")), SoundSource.HOSTILE, (float) 0.5, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_charge")), SoundSource.HOSTILE, (float) 0.5, 1, false);
				}
			}
			for (int index4 = 0; index4 < 9; index4++) {
				WardenRimedParticleProcedure.execute(world, x, y, z);
			}
		});
		CaerulaArborMod.queueServerWork(40, () -> {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_charge")), SoundSource.HOSTILE, 2, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.sonic_charge")), SoundSource.HOSTILE, 2, 1, false);
				}
			}
		});
		CaerulaArborMod.queueServerWork(47, () -> {
			WardenRadBoomProcedure.execute(world, x, y, z, entity);
		});
	}
}
