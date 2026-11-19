package net.apocalypse.careunite.procedures;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

import net.apocalypse.careunite.entity.MoistEnderCrystalEntity;

public class CrytsalToEnderinaProcedure {
	public static void execute(Entity me, Entity owner) {
		Vec3 ownerPos = owner.position();
        Vec3 goal;
        Vec3 v1 = ownerPos.vectorTo(me.position());
        Vec3 v = new Vec3(v1.x, 0, v1.z);
        if (me instanceof MoistEnderCrystalEntity c){
        	c.setOwner(owner);
        }
        if (v.lengthSqr() > 144)
            goal = ownerPos.add(v.normalize().scale(10));
        else{
            RandomSource random = me.level().random;
            int yaw = Mth.nextInt(random, 30, 90);
            double r = Mth.nextDouble(random, 7, 12);
            goal = ownerPos.add(v.normalize().scale(r).yRot((float) Math.toRadians(yaw)));
        }
        if (goal.distanceToSqr(me.position()) > 0.25) {
            if (me instanceof Mob mob){
                mob.getNavigation().moveTo(goal.x, goal.y, goal.z, 1);
            }
        }
	}
}
