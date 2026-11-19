package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.Comparator;

public class GetLivingEntityNumProcedure {
	public static double execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return 0;
		double count = 0;
		final Vec3 _center = new Vec3(x, y, z);
		List<LivingEntity> _entfound = world.getEntitiesOfClass(LivingEntity.class, 
			new AABB(_center, _center).inflate(64 / 2d), e ->{return e != entity;});
		return _entfound.size();
	}
}
