package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class DescSublimationMig1Procedure {
	public static String execute(LevelAccessor world) {
		String key = "";
		String desc = "";
		double lvl = 0;
		lvl = Math.min(CaerulaArborModVariables.MapVariables.get(world).strategy_migration, CaerulaArborModVariables.MapVariables.get(world).strategy_sublimation);
		if (lvl < 1) {
			return "";
		}
		key = "evolution.caerula_aerbor.sublimation.migration." + (int) lvl + "_1";
		desc = Component.translatable(key).getString();
		if ((desc).equals(key)) {
			return "";
		}
		return desc;
	}
}
