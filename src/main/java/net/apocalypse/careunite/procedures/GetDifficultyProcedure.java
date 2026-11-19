package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.network.chat.Component;

import net.apocalypse.careunite.init.CaerulaArborModGameRules;

public class GetDifficultyProcedure {
	public static String execute(LevelAccessor world) {
		return Component.translatable("key.surging_waves").getString() + "\u00B7" + Math.round((world.getLevelData().getGameRules().getInt(CaerulaArborModGameRules.SURGING_WAVES)));
	}
}
