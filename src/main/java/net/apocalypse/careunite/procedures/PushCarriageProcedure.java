package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;

public class PushCarriageProcedure {
	public static void execute(Entity me) {
		if (me == null)
			return;
		if (me instanceof Mob){
			Mob mob = (Mob)me;
			Entity enemy = mob.getTarget();
			double dx;
			double dz;
			if (enemy != null && enemy.isAlive()){
				dx = enemy.getX() - me.getX();
				dz = enemy.getZ() - me.getZ();
			} else {
				dx = mob.getLookAngle().x;
				dz = mob.getLookAngle().z;
			}
			double r = Math.sqrt(dx * dx + dz * dz);
			if (r > 0.001){
				r /= 2.5;
				dx /= r;
				dz /= r;
				me.push(dx, 0, dz);
			}
		}
	}
}
