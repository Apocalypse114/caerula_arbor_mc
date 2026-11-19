package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.UnquenchableFireEntity;

public class DistributeValerioFireAtProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity tgt) {
		if (entity == null || tgt == null)
			return;
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = CaerulaArborModEntities.UNQUENCHABLE_FIRE.get().create(_level);
			if (entityToSpawn instanceof UnquenchableFireEntity fff) {
				fff.setLinked(tgt);
				fff.setOwner(entity);
				fff.setPos(tgt.position());
				_level.addFreshEntity(fff);
			}
		}
	}
}
