package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedRavagerEntity;

public class NearestRidableRavagerProcedure {
	public static Entity execute(LevelAccessor world, double x, double y, double z, Entity entity, double distLimit) {
		if (entity == null)
			return null;
		Entity enemy = null;
		double minDist = 0;
		double d = 0;
		minDist = 999;
		for (Entity entityiterator : world.getEntities(entity, new AABB((x + distLimit), (y + distLimit), (z + distLimit), (x - distLimit), (y - distLimit), (z - distLimit)))) {
			if (!(entityiterator instanceof OceanizedRavagerEntity)) {
				continue;
			}
			if (entityiterator.isVehicle()) {
				continue;
			}
			if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator || (entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entity) {
				continue;
			}
			d = entityiterator != null ? entity.distanceTo(entityiterator) : -1;
			if (d < minDist && d < distLimit) {
				minDist = d;
				enemy = entityiterator;
			}
		}
		return enemy;
	}
}
