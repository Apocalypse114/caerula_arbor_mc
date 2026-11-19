package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.entity.MartusEntity;

public class GetLinkedSeabornProcedure {
	public static Entity execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return null;
		Entity tgt_ent = null;
		Entity tgt_blessed = null;
		double num = 0;
		double max_h = 0;
		double limit = 0;
		double blesses_h = 0;
		double d1 = 0;
		max_h = 999;
		blesses_h = 999;
		for (Entity entityiterator : world.getEntities(entity, new AABB((x + 32), (y + 32), (z + 32), (x - 32), (y - 32), (z - 32)))) {
			if (!(entityiterator instanceof Mob)) {
				continue;
			}
			if (!entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
				continue;
			}
			if (entityiterator instanceof MartusEntity) {
				continue;
			}
			d1 = entityiterator != null ? entity.distanceTo(entityiterator) : -1;
			if (entityiterator.getPersistentData().getBoolean("blessed") && d1 < blesses_h) {
				blesses_h = d1;
				tgt_blessed = entityiterator;
			} else if (d1 < max_h) {
				max_h = d1;
				tgt_ent = entityiterator;
			}
		}
		if (!(tgt_blessed == null)) {
			return tgt_blessed;
		}
		return tgt_ent;
	}
}
