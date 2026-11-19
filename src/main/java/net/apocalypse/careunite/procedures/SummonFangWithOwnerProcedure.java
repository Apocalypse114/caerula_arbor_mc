package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.entity.LivingEntity;

public class SummonFangWithOwnerProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = EntityType.EVOKER_FANGS.spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
			if (entityToSpawn instanceof EvokerFangs fang && entity instanceof LivingEntity owner) {
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				fang.setOwner(owner);
			}
		}
	}
}
