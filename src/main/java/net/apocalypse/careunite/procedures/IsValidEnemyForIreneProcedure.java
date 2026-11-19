package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class IsValidEnemyForIreneProcedure {
	public static boolean execute(Entity ene, Entity entity) {
		if (ene == null || entity == null)
			return false;
		Entity enemy = null;
		double damage = 0;
		double r = 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (!(ene instanceof LivingEntity)) {
			return false;
		}
		if (NotSameTeamProcedure.execute(entity, ene)) {
			return true;
		}
		if (!ene.isAlive()) {
			return false;
		}
		if (ene instanceof Player || (ene instanceof TamableAnimal _tamEnt ? _tamEnt.isTame() : false)) {
			if (!(ene == enemy)) {
				return false;
			}
		}
		if (!(ene instanceof Monster)) {
			return ene == enemy || (ene instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entity;
		}
		if (ene.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:is_humanside")))) {
			if (!(ene == enemy)) {
				return false;
			}
		}
		if (IsSameTeamProcedure.execute(entity, ene)) {
			return false;
		}
		if (ene == entity) {
			return false;
		}
		return true;
	}
}
