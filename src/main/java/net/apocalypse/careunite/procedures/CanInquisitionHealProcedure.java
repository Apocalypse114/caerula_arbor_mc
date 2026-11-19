package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class CanInquisitionHealProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return IsBatalionSuitProcedure.execute(entity) || entity instanceof Player && IsInquisitionSuitProcedure.execute(entity) > 0.5 || entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:homo_sapiens")))
				|| entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:hunters")));
	}
}
