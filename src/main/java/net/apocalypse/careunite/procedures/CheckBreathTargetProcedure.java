package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.entity.OceanizedEnderinaEntity;

public class CheckBreathTargetProcedure {
	public static boolean execute(Entity e, Entity enemy, Entity owner) {
		if (e == null || enemy == null || owner == null)
			return false;
		Entity recentVictim = null;
		Entity recentAttacker = null;
		if (owner instanceof OceanizedEnderinaEntity) {
			if (e.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
				if (!(e == enemy)) {
					return false;
				}
			}
		} else {
			if (!(e instanceof Monster)) {
				if (e == enemy) {
					return true;
				}
				recentVictim = (owner instanceof LivingEntity _entity) ? _entity.getLastHurtMob() : null;
				recentAttacker = (owner instanceof LivingEntity _entity) ? _entity.getLastHurtByMob() : null;
				if (e == recentVictim) {
					return true;
				}
				if (e == recentAttacker) {
					return true;
				}
				if ((e instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == owner) {
					return true;
				}
				return false;
			}
		}
		return true;
	}
}
