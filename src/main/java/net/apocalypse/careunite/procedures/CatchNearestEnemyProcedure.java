package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

public class CatchNearestEnemyProcedure {
	public static Entity execute(LevelAccessor world, double x, double y, double z, Entity obj) {
		if (obj == null)
			return null;
		Entity enemy = null;
		double minDist = 0;
		double d = 0;
		minDist = 999;
		for (Entity entityiterator : world.getEntities(obj, new AABB((x + 4), (y + 4), (z + 4), (x - 4), (y - 4), (z - 4)))) {
			if (!(entityiterator instanceof LivingEntity)) {
				continue;
			}
			if (IsValidEnemyForIreneProcedure.execute(entityiterator, obj)) {
				d = entityiterator != null ? obj.distanceTo(entityiterator) : -1;
				if (d <= 4) {
					if (d < minDist) {
						minDist = d;
						enemy = entityiterator;
					}
				}
			}
		}
		return enemy;
	}
}
