package net.apocalypse.careunite.procedures;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

public class IllusionToOwnerProcedure {
	public static void execute(Entity me, Entity owner) {
		Vec3 ownerPos = owner.position();
        Vec3 goal;
        Vec3 v = ownerPos.vectorTo(me.position());
        Vec3 v1 = new Vec3(v.x, 0, v.z);
        if (v1.lengthSqr() > 30.25)
            goal = ownerPos.add(v1.normalize().scale(5));
        else{
            RandomSource random = me.level().random;
            int yaw = Mth.nextInt(random, 30, 90);
            double r = Mth.nextDouble(random, 3, 5);
            goal = ownerPos.add(v1.normalize().scale(r).yRot((float) Math.toRadians(yaw)));
        }
        if (goal.distanceToSqr(me.position()) > 0.25) {
            if (me instanceof Mob mob){
                mob.getNavigation().moveTo(goal.x, goal.y, goal.z, 1);
            }
        }
	}
}
