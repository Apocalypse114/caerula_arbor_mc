package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.CreeperFishEntity;

import java.util.Comparator;

public class CatFollowCreeperProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity creeper = null;
		Entity enemy = null;
		creeper = (Entity) world.getEntitiesOfClass(CreeperFishEntity.class, AABB.ofSize(new Vec3(x, y, z), 24, 24, 24), e -> true).stream().sorted(new Object() {
			Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
				return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
			}
		}.compareDistOf(x, y, z)).findFirst().orElse(null);
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (creeper == null) {
			return;
		}
		if (!creeper.isAlive()) {
			return;
		}
		if (!(enemy == null) && enemy.isAlive()) {
			return;
		}
		if (Math.random() < 0.25) {
			if (entity instanceof Mob _entity)
				_entity.getNavigation().moveTo((creeper.getX()), (creeper.getY()), (creeper.getZ()), 1);
		}
	}
}
