package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.apocalypse.careunite.init.CaerulaArborModGameRules;

public class IsDifficultProcedure {
	public static boolean execute(LevelAccessor world) {
		return (world.getLevelData().getGameRules().getInt(CaerulaArborModGameRules.SURGING_WAVES)) > 0;
	}
}
