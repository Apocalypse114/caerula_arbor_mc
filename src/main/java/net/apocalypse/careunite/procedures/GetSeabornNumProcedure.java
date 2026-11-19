package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.Comparator;

public class GetSeabornNumProcedure {
	public static double execute(LevelAccessor world, double x, double y, double z) {
		double count = 0;
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(32 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (IsSeabornProcedure.execute(entityiterator)) {
					if (!(IsSeabornBossProcedure.execute(entityiterator) || IsSeabornPetProcedure.execute(entityiterator))) {
						count = count + 1;
					}
				}
			}
		}
		return count;
	}
}
