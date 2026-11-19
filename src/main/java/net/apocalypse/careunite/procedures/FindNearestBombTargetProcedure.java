package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.AegirMortarEntity;

public class FindNearestBombTargetProcedure {
	public static Entity execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return null;
		double minDist = 0;
		Entity enemy = null;
		Entity curEnemy = null;
		minDist = 999;
		for (Entity entityiterator : world.getEntities(entity, new AABB((x + 72), (y + 64), (z + 72), (x - 72), (y - 72), (z - 72)))) {
			if (!(entityiterator instanceof LivingEntity)) {
				continue;
			}
			if (!entityiterator.isAlive()) {
				continue;
			}
			if (entityiterator instanceof TamableAnimal _tamEnt ? _tamEnt.isTame() : false) {
				continue;
			}
			if (!(entityiterator instanceof Monster)) {
				if (!((entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof Player || (entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof AegirMortarEntity)) {
					continue;
				}
			}
			if (IsSameTeamProcedure.execute(entity, entityiterator)) {
				continue;
			}
			if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) < minDist) {
				minDist = entityiterator != null ? entity.distanceTo(entityiterator) : -1;
				enemy = entityiterator;
			}
		}
		return enemy;
	}
}
