package net.apocalypse.careunite.procedures;

import org.checkerframework.checker.units.qual.g;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.SpecterEntity;

import java.util.Comparator;

public class IsSpecterAroundProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		Entity g = null;
		g = (Entity) world.getEntitiesOfClass(SpecterEntity.class, AABB.ofSize(new Vec3(x, y, z), 64, 64, 64), e -> true).stream().sorted(new Object() {
			Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
				return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
			}
		}.compareDistOf(x, y, z)).findFirst().orElse(null);
		if (!(g == null) && g.isAlive()) {
			return true;
		}
		return true;
	}
}
