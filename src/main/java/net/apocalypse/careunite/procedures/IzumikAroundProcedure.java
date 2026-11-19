package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;

import net.apocalypse.careunite.entity.IzumikEntity;

public class IzumikAroundProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		return !world.getEntitiesOfClass(IzumikEntity.class, AABB.ofSize(new Vec3(x, y, z), 64, 64, 64), e -> true).isEmpty();
	}
}
