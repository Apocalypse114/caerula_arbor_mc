package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class DescSublimationBreedProcedure {
	public static String execute(LevelAccessor world) {
		double lvl = 0;
		lvl = Math.min(CaerulaArborModVariables.MapVariables.get(world).strategy_breed, CaerulaArborModVariables.MapVariables.get(world).strategy_sublimation);
		if (lvl < 1) {
			return "";
		}
		return Component.translatable(("evolution.caerula_aerbor.sublimation.breed." + (int) lvl)).getString();
	}
}
