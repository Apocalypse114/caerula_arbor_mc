package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

public class ReduceLightsWithDamageProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity, double amount) {
		if (entity == null || sourceentity == null)
			return;
		if (entity == sourceentity) {
			return;
		}
		if (entity instanceof Player) {
			ReducePlayerLightsProcedure.execute(world, entity, Math.min(amount * 0.0025, 0.25));
		}
	}
}
