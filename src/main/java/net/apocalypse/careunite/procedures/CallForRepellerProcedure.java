package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.TideDeathrepellerEntity;

import java.util.Comparator;

public class CallForRepellerProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity call = null;
		call = (Entity) world.getEntitiesOfClass(TideDeathrepellerEntity.class, AABB.ofSize(new Vec3(x, y, z), 96, 96, 96), e -> true).stream().sorted(new Object() {
			Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
				return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
			}
		}.compareDistOf(x, y, z)).findFirst().orElse(null);
		if (!(call == null) && !(call instanceof Mob _mobEnt2 && _mobEnt2.isAggressive()) && (call != null ? entity.distanceTo(call) : -1) > 8) {
			if (call instanceof Mob _entity)
				_entity.getNavigation().moveTo(x, y, z, 1);
		}
	}
}
