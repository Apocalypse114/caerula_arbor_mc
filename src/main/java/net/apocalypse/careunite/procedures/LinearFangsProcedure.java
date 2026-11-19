package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;

public class LinearFangsProcedure {
	public static void execute(LevelAccessor world, Entity entity, double index) {
		if (entity == null)
			return;
		double dist = 0;
		double vx = 0;
		double vy = 0;
		double vz = 0;
		double fy = 0;
		Entity enemy = null;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (!(enemy == null)) {
			dist = Math.round(enemy != null ? entity.distanceTo(enemy) : -1);
			vx = enemy.getX() - entity.getX();
			vy = enemy.getY() - entity.getY();
			vz = enemy.getZ() - entity.getZ();
			vx = entity.getX() + vx * (index / dist);
			vy = entity.getY() + vy * (index / dist);
			vz = entity.getZ() + vz * (index / dist);
			fy = FindGroundProcedure.execute(world, vx, vy, vz);
			if (fy <= 114513) {
				SummonFangWithOwnerProcedure.execute(world, vx, fy, vz, entity);
			}
		}
	}
}
