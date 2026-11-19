package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.TypheousSignEntity;

public class TypheousDropSIgnProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity tgt) {
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = CaerulaArborModEntities.TYPHEOUS_SIGN.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn instanceof TypheousSignEntity s) {
				s.withTarget(tgt);
			}
		}
	}
}
