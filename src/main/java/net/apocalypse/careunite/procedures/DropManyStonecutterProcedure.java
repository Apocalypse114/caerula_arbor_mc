package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class DropManyStonecutterProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		for (int index0 = 0; index0 < 32; index0++) {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = CaerulaArborModEntities.CHISELER_FISH.get().spawn(_level,
						BlockPos.containing(x + Mth.nextInt(RandomSource.create(), -1, 1), y + Mth.nextInt(RandomSource.create(), 0, 2), z + Mth.nextInt(RandomSource.create(), -1, 1)), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				}
			}
		}
	}
}
