package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;

public class RemoveTargetProcedure {
	public static void execute(Entity entity) {
		if (entity instanceof LivingEntity living){
            if (living instanceof Mob mob){
                mob.setTarget(null);
                mob.setLastHurtByMob(null);
                mob.setAggressive(false);
                mob.setLastHurtByPlayer(null);
            }
            if (living instanceof Animal animal){
                animal.setTarget(null);
            }
            if (living instanceof NeutralMob n){
                n.stopBeingAngry();
                n.setPersistentAngerTarget(null);
                n.setRemainingPersistentAngerTime(0);
            }
        }
	}
}
