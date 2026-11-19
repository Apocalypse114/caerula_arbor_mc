package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class VexChargeToSlayerProcedure {
	public static void execute(Entity another, Entity me) {
		if (another == null || me == null)
			return;
		Vec3 offset = me.position().add(0,1,0).add(another.position().reverse());
        if(offset.lengthSqr() <= 9) return;
        offset = offset.normalize().scale(0.5);
        another.setDeltaMovement(offset);
	}
}
