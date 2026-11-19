package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

public class BombArrowProcedure {
	public static void execute(LevelAccessor world, Entity immediatesourceentity) {
		if (immediatesourceentity == null)
			return;
		if (immediatesourceentity.getPersistentData().getBoolean("ComplexChitinCB") || immediatesourceentity.getPersistentData().getBoolean("TrailriteCB")) {
			if (immediatesourceentity.getPersistentData().getBoolean("TrailriteShotBomb")) {
				DedonateCopperProcedure.execute(world, immediatesourceentity.getX(), immediatesourceentity.getY(), immediatesourceentity.getZ());
			} else if (immediatesourceentity.getPersistentData().getBoolean("ComplexBomb")) {
				DedonateTrialProcedure.execute(world, immediatesourceentity.getX(), immediatesourceentity.getY(), immediatesourceentity.getZ());
			}
			if (!immediatesourceentity.level().isClientSide())
				immediatesourceentity.discard();
		}
	}
}
