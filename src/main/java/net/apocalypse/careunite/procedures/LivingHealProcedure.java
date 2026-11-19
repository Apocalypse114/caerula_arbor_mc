package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class LivingHealProcedure {
	public static void execute(Entity entity, double amount) {
		if (entity == null)
			return;
		if(entity instanceof LivingEntity living){
            living.heal((float) amount);
        }
	}
}
