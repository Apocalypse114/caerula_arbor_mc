package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.entity.MartusEntity;

public class GetMostHealthEntProcedure {
	public static Entity execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return null;
		Entity tgt_ent = null;
		double num = 0;
		double max_h = 0;
		double limit = 0;
		limit = 2;
		if (CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting > 3) {
			limit = 3;
		}
		for (Entity entityiterator : world.getEntities(entity, new AABB((x + 32), (y + 32), (z + 32), (x - 32), (y - 32), (z - 32)))) {
			if (NotSameTeamProcedure.execute(entity, entityiterator)) {
				continue;
			}
			if (!(entityiterator instanceof Mob)) {
				continue;
			}
			if (!IsSeabornProcedure.execute(entityiterator)) {
				continue;
			}
			if (entityiterator instanceof MartusEntity) {
				continue;
			}
			if (entityiterator.getPersistentData().getBoolean("blessed")) {
				num = num + 1;
				continue;
			}
			if (num >= limit) {
				break;
			}
			if ((entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) > max_h) {
				max_h = entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1;
				tgt_ent = entityiterator;
			}
		}
		return tgt_ent;
	}
}
