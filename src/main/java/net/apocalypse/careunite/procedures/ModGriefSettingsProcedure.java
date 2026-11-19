package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameRules;

import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

public class ModGriefSettingsProcedure {
	public static boolean execute(LevelAccessor world) {
		if (world.isClientSide()) {
			return false;
		}
		if (CaerulaConfigsConfiguration.BREAKABLE.get()) {
			return world.getLevelData().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
		}
		return false;
	}
}
