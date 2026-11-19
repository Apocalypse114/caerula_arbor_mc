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

public class IzumikSpawnProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double range = 0;
		double t = 0;
		double tgtX = 0;
		double tgtZ = 0;
		double validY = 0;
		validY = 114514;
		for (int index0 = 0; index0 < 24; index0++) {
			range = Mth.nextInt(RandomSource.create(), 28, 42);
			t = Mth.nextDouble(RandomSource.create(), 0, 6.283);
			tgtX = x + range * Math.sin(t);
			tgtZ = z + range * Math.cos(t);
			validY = FindValidYOffsprProcedure.execute(world, x, y, z, tgtX, y + 4, tgtZ);
			if (validY < 114513) {
				break;
			}
		}
		if (validY < 114513) {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.IZUMIK_OFFSPRING.get().spawn(_level, BlockPos.containing(tgtX, validY, tgtZ), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					EmrelySyncTeamProcedure.execute(entity, entityToSpawn);
					_level.sendParticles(ParticleTypes.CLOUD, tgtX, validY, tgtZ, 32, 0.5, 0.5, 0.5, 0.15);
				}
			}
		}
	}
}
