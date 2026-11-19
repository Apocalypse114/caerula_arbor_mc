package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class RecallSpecterProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double tickCount = 0;
		if (!entity.level().isClientSide())
			entity.discard();
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.SPECTER_GLITTER.get()), x, (y + 0.75), z, 64, 0.75, 0.75, 0.75, 0.1);
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = CaerulaArborModEntities.SPECTER.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				SyncTeamAndTargetProcedure.execute(entity, entityToSpawn);
			}
		}
	}
}
