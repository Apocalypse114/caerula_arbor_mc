package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.CaerulaArborMod;

public class WitheriaDoHurtTargetProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:wither_scythe_attack")), SoundSource.NEUTRAL, 8, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:wither_scythe_attack")), SoundSource.NEUTRAL, 8, 1, false);
			}
		}
		CaerulaArborMod.queueServerWork(7, () -> {
			if (sourceentity.isAlive()) {
				if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 4) {
					WitheriaSweepProcedure.execute(world, sourceentity, 0.5, 1);
				}
			}
		});
	}
}
