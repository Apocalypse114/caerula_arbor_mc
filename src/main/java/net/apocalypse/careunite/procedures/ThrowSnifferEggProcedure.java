package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class ThrowSnifferEggProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double r = Mth.nextDouble(RandomSource.create(), 0, 6.283);
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = CaerulaArborModEntities.OCEANIZED_SNIFFER_EGG.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn != null) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				entityToSpawn.push(Math.sin(r), 1, Math.cos(r));
				EmrelySyncTeamProcedure.execute(entity, entityToSpawn);
			}
		}
	}
}
