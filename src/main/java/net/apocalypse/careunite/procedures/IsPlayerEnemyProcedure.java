package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;

public class IsPlayerEnemyProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		Entity enemy = null;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (enemy == null) {
			return false;
		}
		return enemy instanceof Player;
	}
}
