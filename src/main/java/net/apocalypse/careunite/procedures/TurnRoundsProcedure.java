package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class TurnRoundsProcedure {
	public static void execute(Entity another, Entity me) {
		if (another == null || me == null)
			return;
        Vec3 offset = another.position().add(me.position().reverse());
        if(offset.lengthSqr() <= 0.25) return;
        Vec3 delta = new Vec3(-offset.z , 0.15, offset.x).normalize().scale(0.5);
        another.setDeltaMovement(delta);
	}
}
