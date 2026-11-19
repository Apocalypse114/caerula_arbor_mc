package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;

public class PurchaseEnemyProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (!(enemy == null) && enemy.isAlive()) {
			if ((enemy != null ? entity.distanceTo(enemy) : -1) > 4) {
				{
					Entity _ent = entity;
					_ent.teleportTo((enemy.getX()), (enemy.getY()), (enemy.getZ()));
					if (_ent instanceof ServerPlayer _serverPlayer)
						_serverPlayer.connection.teleport((enemy.getX()), (enemy.getY()), (enemy.getZ()), _ent.getYRot(), _ent.getXRot());
				}
			}
		}
	}
}
