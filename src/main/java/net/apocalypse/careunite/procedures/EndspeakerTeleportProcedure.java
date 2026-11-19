package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;

public class EndspeakerTeleportProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		boolean canAttack = false;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		canAttack = !(enemy == null) && enemy.isAlive();
		if (canAttack) {
			{
				Entity _ent = entity;
				_ent.teleportTo((enemy.getX()), (enemy.getY()), (enemy.getZ()));
				if (_ent instanceof ServerPlayer _serverPlayer)
					_serverPlayer.connection.teleport((enemy.getX()), (enemy.getY()), (enemy.getZ()), _ent.getYRot(), _ent.getXRot());
			}
		}
	}
}
