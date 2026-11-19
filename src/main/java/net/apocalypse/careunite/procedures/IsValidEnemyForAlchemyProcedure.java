package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.NoviceAlchemistEntity;

public class IsValidEnemyForAlchemyProcedure {
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
		if ((entity instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null) == ene) {
			return false;
		}
		if (ene instanceof NoviceAlchemistEntity || ene instanceof Player || IsAlchemyProcedure.execute(ene)) {
			return ene == (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) || entity == (ene instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null);
		}
		return true;
	}
}
