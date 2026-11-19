package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

public class IsValidEnemyForSeabornProcedure {
	public static boolean execute(Entity ene, Entity entity) {
		if (ene == null || entity == null)
			return false;
		if (entity == ene) {
			return false;
		}
		if (!(ene instanceof LivingEntity)) {
			return false;
		}
		if (NotSameTeamProcedure.execute(entity, ene)) {
			return true;
		}
		if (IsSameTeamProcedure.execute(entity, ene)) {
			return false;
		}
		if (IsSeabornProcedure.execute(ene)) {
			return ene == (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) || entity == (ene instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null);
		}
		return true;
	}
}
