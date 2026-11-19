package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class DistributeGuardianProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double r = 0;
		double d = 0;
		double tx = 0;
		double tz = 0;
		double ty = 0;
		for (int index0 = 0; index0 < 3; index0++) {
			r = Mth.nextDouble(RandomSource.create(), 0, 6.283);
			d = Mth.nextDouble(RandomSource.create(), 1.5, 3);
			tx = x + d * Math.cos(r);
			tz = z + d * Math.sin(r);
			if (ty < 114110) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = CaerulaArborModEntities.PROMOTED_GUARDIAN.get().spawn(_level, BlockPos.containing(tx, y, tz), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
						EmrelySyncTeamProcedure.execute(entity, entityToSpawn);
					}
				}
			}
		}
	}
}
