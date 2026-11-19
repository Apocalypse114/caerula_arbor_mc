package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class PullToGladiiaProcedure {
	public static void execute(Entity another, Entity me) {
		if (another == null || me == null)
			return;
		Vec3 offset = me.position().add(another.position().reverse());
        if(offset.lengthSqr() <= 0.01)return;
        offset = offset.normalize().scale(1.5);
        another.push(offset.x, offset.y, offset.z);
	}
}
