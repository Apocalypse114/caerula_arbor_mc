package net.apocalypse.careunite.procedures;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

public class SefsefProcedure {
	public static boolean execute(DamageSource damagesource) {
		if (damagesource == null)
			return false;
		return damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:inv_killer")));
	}
}
