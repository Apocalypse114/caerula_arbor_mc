package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;

public class IsSeabornPetProcedure {
	public static final TagKey<EntityType<?>> SEABORN = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanpet"));
	
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return entity.getType().is(SEABORN);
	}
}
