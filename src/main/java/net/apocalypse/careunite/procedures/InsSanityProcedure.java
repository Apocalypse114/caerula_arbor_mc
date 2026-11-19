package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class InsSanityProcedure {
	public static void execute(Entity entity, double amplifier) {
		if (entity == null)
			return;
		if (!entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:immue_to_inst_sanity")))) {
			DeductPlayerSanityProcedure.execute(entity, 125 * (amplifier + 1));
		}
	}
}
