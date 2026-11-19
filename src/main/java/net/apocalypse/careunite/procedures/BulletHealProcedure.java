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

public class BulletHealProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		CaerulaArborMod.queueServerWork(16, () -> {
			if (entity.isAlive()) {
				RangedCommonHealProcedure.execute(world, x, y, z, entity);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:medic_normal")), SoundSource.HOSTILE, (float) 1.8,
								(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:medic_normal")), SoundSource.HOSTILE, (float) 1.8, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
					}
				}
			}
		});
	}
}
