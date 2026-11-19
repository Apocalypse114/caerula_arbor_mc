package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedSnifferEggEntity;

import java.util.List;
import java.util.Comparator;

public class GetEggNumProcedure {
	public static int execute(LevelAccessor world, double x, double y, double z) {
		final Vec3 _center = new Vec3(x, y, z);
		List<OceanizedSnifferEggEntity> _entfound = world.getEntitiesOfClass(OceanizedSnifferEggEntity.class, 
			new AABB(_center, _center).inflate(48 / 2d),
			e -> e.isAlive());
		return _entfound.size();
	}
}
