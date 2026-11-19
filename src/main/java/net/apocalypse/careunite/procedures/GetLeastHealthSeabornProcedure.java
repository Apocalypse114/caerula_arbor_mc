package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.entity.OceanizedEndermiteEntity;

public class GetLeastHealthSeabornProcedure {
	public static Entity execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return null;
		Entity tgt_ent = null;
		double num = 0;
		double max_h = 0;
		double limit = 0;
		max_h = Double.POSITIVE_INFINITY;
		for (Entity entityiterator : world.getEntities(entity, new AABB((x + 32), (y + 24), (z + 32), (x - 32), (y - 24), (z - 32)))) {
			if (!(entityiterator instanceof Monster)) {
				continue;
			}
			if (entityiterator instanceof OceanizedEndermiteEntity) {
				continue;
			}
			if (!entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
				continue;
			}
			if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:bossoffspring")))) {
				continue;
			}
			if ((entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) < max_h) {
				max_h = entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1;
				tgt_ent = entityiterator;
			}
		}
		return tgt_ent;
	}
}
