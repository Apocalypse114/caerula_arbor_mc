package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class DragonBreathChatgeProcedure {
	public static void execute(Entity another, Entity me) {
        if (another == null || me == null)
            return;
        Vec3 offset = another.position()
                .vectorTo(me.position().add(0,0.25,0));
        if(offset.lengthSqr() <= 0.05){
        	another.setDeltaMovement(Vec3.ZERO);
        	return;
        }
        offset = offset.normalize().scale(0.4);
        another.setDeltaMovement(offset);
    }
}
