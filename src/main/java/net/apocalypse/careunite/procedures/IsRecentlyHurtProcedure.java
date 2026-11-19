package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

public class IsRecentlyHurtProcedure {
	public static boolean execute(Entity entity, Entity other) {
		if (entity == null || other == null)
			return false;
		return entity instanceof Player && (((entity instanceof LivingEntity _entity) ? _entity.getLastHurtByMob() : null) == other || ((entity instanceof LivingEntity _entity) ? _entity.getLastHurtMob() : null) == other)
				|| (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == other || (other instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entity;
	}
}
