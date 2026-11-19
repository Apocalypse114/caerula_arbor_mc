package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class RetreatEntityProcedure {
	public static void execute(Entity A, Entity B, double force) {
		if (A == null || B == null)
			return;
		Vec3 pushVec = A.position().vectorTo(B.position());
        if(pushVec.lengthSqr() < 0.0001) pushVec = new Vec3(0,0,1);
        else pushVec = pushVec.normalize();
        pushVec = pushVec.scale(force);
        B.push(pushVec.x, pushVec.y, pushVec.z);
	}
}
