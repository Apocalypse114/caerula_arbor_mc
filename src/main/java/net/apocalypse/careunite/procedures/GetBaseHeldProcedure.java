package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.apocalypse.careunite.AlchemyCreation;

public class GetBaseHeldProcedure {
	public static double execute(Entity entity, boolean set, double num) {
		if (entity == null)
			return -1;
		if (entity instanceof AlchemyCreation cc){
			if (set){
				if (num > 0) cc.gainBase((int)num);
				else cc.lostBase((int)(-num), false);
			} else {
				return cc.getBaseHeld();
			}
		}
		return -1;
	}
}
