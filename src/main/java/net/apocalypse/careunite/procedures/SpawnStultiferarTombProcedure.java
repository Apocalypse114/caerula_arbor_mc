package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.minecraft.world.entity.EntityType;

public class SpawnStultiferarTombProcedure {
	public static Entity execute(LevelAccessor world, double x, double y, double z, boolean small, double atk, double hlth) {
		if (world instanceof ServerLevel _level) {
			EntityType<?> type = null;
			if (small) {
				type = CaerulaArborModEntities.TOMBSTONE_STULTIFERA_SMALL.get();
			} else {
				type = CaerulaArborModEntities.TOMBSTONE_STULTIFERA.get();
			}
			Entity entityToSpawn = type.create(_level);
			if (entityToSpawn instanceof LivingEntity living) {
				entityToSpawn.setPos(x, y, z);
				entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
				living.getAttribute(Attributes.MAX_HEALTH).setBaseValue(hlth);
				living.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(atk);
				living.setHealth((float)hlth);
				_level.addFreshEntity(living);
			}
			return entityToSpawn;
		}
		return null;
	}
}
