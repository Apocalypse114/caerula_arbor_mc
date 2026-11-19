package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;

public class GiveSeabornForgiveProcedure {
	public static InteractionResult execute(Entity entity) {
		if (entity == null)
			return InteractionResult.PASS;
		if (entity.getPersistentData().getBoolean("seabornForgive")) {
			return InteractionResult.PASS;
		}
		entity.getPersistentData().putBoolean("seabornForgive", true);
		return InteractionResult.SUCCESS;
	}
}
