package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.AbsorberLimbEntity;

import java.util.List;
import java.util.Comparator;

public class GetLimbNumProcedure {
	public static double execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return 0;
		final Vec3 _center = new Vec3(x, y, z);
		List<AbsorberLimbEntity> _entfound = world.getEntitiesOfClass(AbsorberLimbEntity.class, 
		new AABB(_center, _center).inflate(48 / 2d), AbsorberLimbEntity::isAlive);
		return _entfound.size();
	}
}
