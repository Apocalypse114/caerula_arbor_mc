package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class DescSublimationBreed1Procedure {
	public static String execute(LevelAccessor world) {
		String desc = "";
		String key = "";
		double lvl = 0;
		lvl = Math.min(CaerulaArborModVariables.MapVariables.get(world).strategy_breed, CaerulaArborModVariables.MapVariables.get(world).strategy_sublimation);
		if (lvl < 1) {
			return "";
		}
		key = "evolution.caerula_aerbor.sublimation.breed." + (int) lvl + "_1";
		desc = Component.translatable(key).getString();
		if ((desc).equals(key)) {
			return "";
		}
		return desc;
	}
}
