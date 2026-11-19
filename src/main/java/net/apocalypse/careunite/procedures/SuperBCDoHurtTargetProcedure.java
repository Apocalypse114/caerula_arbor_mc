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

public class SuperBCDoHurtTargetProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		double amplifi = 0;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cat.hiss")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.85, 1.15));
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cat.hiss")), SoundSource.HOSTILE, 1, (float) Mth.nextDouble(RandomSource.create(), 0.85, 1.15), false);
			}
		}
		CaerulaArborMod.queueServerWork(9, () -> {
			if (sourceentity.isAlive()) {
				if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 10) {
					SuperCatRangedProcedure.execute(world, entity.getX(), entity.getY(), entity.getY(), sourceentity);
				}
			}
		});
		CaerulaArborMod.queueServerWork(14, () -> {
			if (sourceentity.isAlive()) {
				if ((entity != null ? sourceentity.distanceTo(entity) : -1) <= 15) {
					SuperCatRangedProcedure.execute(world, entity.getX(), entity.getY(), entity.getY(), sourceentity);
				}
			}
		});
	}
}
