package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModGameRules;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.TideutantRockSpiderEntity;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;
import net.apocalypse.careunite.CaerulaArborMod;

public class TimedSpawnExcrescenceProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double dura = 0;
		if (entity.isAlive()) {
			dura = entity instanceof TideutantRockSpiderEntity _datEntI ? _datEntI.getEntityData().get(TideutantRockSpiderEntity.DATA_duration) : 0;
			if (dura > 0) {
				if (entity instanceof TideutantRockSpiderEntity _datEntSetI)
					_datEntSetI.getEntityData().set(TideutantRockSpiderEntity.DATA_duration, (int) (dura - 1));
			}
			if (GetExcreNumProcedure.execute(world, x, y, z) > 32) {
				return;
			}
			if (entity.tickCount % 120 == 75
					&& GetSeabornNumProcedure.execute(world, x, y, z) < Math.min((double) CaerulaConfigsConfiguration.CLONE_NUM.get(), (world.getLevelData().getGameRules().getInt(CaerulaArborModGameRules.CLONE_NUMBER_LIMIT))) * 2) {
				if (entity instanceof TideutantRockSpiderEntity) {
					((TideutantRockSpiderEntity) entity).setAnimation("animation.tidutant_rock_spider.skill");
				}
				if (entity instanceof TideutantRockSpiderEntity _datEntSetI)
					_datEntSetI.getEntityData().set(TideutantRockSpiderEntity.DATA_duration, 20);
				CaerulaArborMod.queueServerWork(11, () -> {
					if (entity.isAlive()) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zombie.destroy_egg")), SoundSource.HOSTILE, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zombie.destroy_egg")), SoundSource.HOSTILE, 1, 1, false);
							}
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = CaerulaArborModEntities.TIDUTANT_EXCRESCENCE.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
							if (entityToSpawn != null) {
								entityToSpawn.setYRot((float) Mth.nextDouble(RandomSource.create(), 0, 360));
								entityToSpawn.setYBodyRot((float) Mth.nextDouble(RandomSource.create(), 0, 360));
								entityToSpawn.setYHeadRot((float) Mth.nextDouble(RandomSource.create(), 0, 360));
								entityToSpawn.setDeltaMovement((Mth.nextDouble(RandomSource.create(), -0.2, 0.2)), 0, (Mth.nextDouble(RandomSource.create(), -0.2, 0.2)));
							}
						}
					}
				});
			}
		}
	}
}
