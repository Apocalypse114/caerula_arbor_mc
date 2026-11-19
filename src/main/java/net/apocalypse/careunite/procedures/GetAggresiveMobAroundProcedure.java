package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.Comparator;

public class GetAggresiveMobAroundProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return false;
		if (world.isClientSide()) {
			return false;
		}
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 4
						&& ((entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entity || entityiterator == ((entity instanceof LivingEntity _entity) ? _entity.getLastHurtByMob() : null))) {
					return true;
				}
			}
		}
		return false;
	}
}
