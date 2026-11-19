package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.LingeringFractalEntity;
import net.apocalypse.careunite.entity.LineringPathshaperEntity;

public class SummonSyncedFractalProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double dx = 0;
		double dz = 0;
		dx = Mth.nextDouble(RandomSource.create(), -0.5, 0.5);
		dz = Mth.nextDouble(RandomSource.create(), -0.5, 0.5);
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = null;
			if (entity instanceof LineringPathshaperEntity || entity instanceof LingeringFractalEntity) {
				entityToSpawn = CaerulaArborModEntities.LINGERING_FRACTAL.get().spawn(_level, BlockPos.containing(x + dx, y, z + dz), MobSpawnType.MOB_SUMMONED);
			} else {
				entityToSpawn = CaerulaArborModEntities.ROUTE_FRACTAL.get().spawn(_level, BlockPos.containing(x + dx, y, z + dz), MobSpawnType.MOB_SUMMONED);
			}
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				EmrelySyncTeamProcedure.execute(entity, entityToSpawn);
			}
			_level.sendParticles(ParticleTypes.CLOUD, (x + dx), y, (z + dz), 48, 0.5, 1, 0.5, 0.1);
		}
	}
}
