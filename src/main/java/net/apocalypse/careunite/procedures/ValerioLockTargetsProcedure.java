package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.Comparator;

public class ValerioLockTargetsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double limit) {
		if (entity == null)
			return;
		double num = 0;
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(48 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (IsValidEnemyForAlchemyProcedure.execute(entityiterator, entity)) {
					num++;
					DistributeValerioFireAtProcedure.execute(world, 0, 0, 0, entity, entityiterator);
					if (num >= limit) {
						break;
					}
				}
			}
		}
	}
}
