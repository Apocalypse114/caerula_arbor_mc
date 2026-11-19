package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.AlchemyCreation;

public class IsAlchemyStunnedProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (entity instanceof AlchemyCreation cc){
			return cc.isStunned();
		}
		return false;
	}
}
