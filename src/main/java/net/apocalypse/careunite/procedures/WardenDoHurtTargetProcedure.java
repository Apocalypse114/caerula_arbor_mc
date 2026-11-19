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

import net.apocalypse.careunite.CaerulaArborMod;

public class WardenDoHurtTargetProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double amplifi = 0;
		CaerulaArborMod.queueServerWork(10, () -> {
			if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 6) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.attack_impact")), SoundSource.HOSTILE, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.attack_impact")), SoundSource.HOSTILE, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), 1, false);
					}
				}
				WardenRangedProcedure.execute(world, sourceentity, false, 1, x, y, z);
			}
		});
	}
}
