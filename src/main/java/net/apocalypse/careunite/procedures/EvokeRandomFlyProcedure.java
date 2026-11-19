package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModGameRules;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;
import net.minecraft.world.entity.EntityType;

public class EvokeRandomFlyProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double angl = 0;
		double d = 0;
		double tx = 0;
		double tz = 0;
		double rd = 0;
		if (GetSeabornNumProcedure.execute(world, x, y, z) >= Math.min((double) CaerulaConfigsConfiguration.CLONE_NUM.get(), (world.getLevelData().getGameRules().getInt(CaerulaArborModGameRules.CLONE_NUMBER_LIMIT))) * 2) {
			return;
		}
		angl = Mth.nextDouble(RandomSource.create(), 0, 6.283);
		d = Mth.nextDouble(RandomSource.create(), 1, 2);
		tx = x + d * Math.sin(angl);
		tz = z + d * Math.cos(angl);
		rd = Mth.nextDouble(RandomSource.create(), 0, 0.975);
		if (CaerulaArborModVariables.MapVariables.get(world).strategy_grow >= 4) {
			rd = Mth.nextDouble(RandomSource.create(), 0, 1);
		}
		if (world instanceof ServerLevel _level) {
			EntityType<?> type = null;
			if (rd < 0.5) {
				type = CaerulaArborModEntities.OCEANIZED_VEX.get();
			} else if (rd < 0.65) {
				type = CaerulaArborModEntities.FLY_FISH.get();
			} else if (rd < 0.725) {
				type = CaerulaArborModEntities.OCEANIZED_BEE.get();
			} else if (rd < 0.8) {
				type = CaerulaArborModEntities.OCEANIZED_SPIDER.get();
			} else if (rd < 0.95) {
				type = CaerulaArborModEntities.FLOATER_PROKARYOTE.get();
			} else if (rd < 0.975) {
				type = CaerulaArborModEntities.FLEE_FISH.get();
			} else {
				type = CaerulaArborModEntities.CAERULA_OFFSPRING.get();
			}
			Entity entityToSpawn = type.spawn(_level, BlockPos.containing(tx, y + 1.5, tz), MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				EmrelySyncTeamProcedure.execute(entity, entityToSpawn);
			}
			_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.EDERMAN_PTC.get()), tx, (y + 2), tz, 16, 0.5, 0.5, 0.5, 0.15);
		}
	}
}
