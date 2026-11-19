package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class DropASliderProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.CLOUD, x, (y + 1.8), z, 32, 1, 1, 1, 0.1);
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = CaerulaArborModEntities.OCEANIZED_ENDERMITE.get().spawn(_level, BlockPos.containing(x, y + 1.8, z), MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				EmrelySyncTeamProcedure.execute(entity, entityToSpawn);
			}
		}
	}
}
