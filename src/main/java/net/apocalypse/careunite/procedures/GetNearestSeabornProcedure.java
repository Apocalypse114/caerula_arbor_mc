package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class GetNearestSeabornProcedure {
	public static Entity execute(LevelAccessor world, double x, double y, double z, Entity obj) {
		if (obj == null)
			return null;
		Entity enemy = null;
		double minDist = 0;
		double d = 0;
		minDist = 999;
		for (Entity entityiterator : world.getEntities(obj, new AABB((x + 32), (y + 32), (z + 32), (x - 32), (y - 32), (z - 32)))) {
			if (!(entityiterator instanceof LivingEntity)) {
				continue;
			}
			if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
				d = entityiterator != null ? obj.distanceTo(entityiterator) : -1;
				if (d < minDist) {
					minDist = d;
					enemy = entityiterator;
				}
			}
		}
		return enemy;
	}
}
