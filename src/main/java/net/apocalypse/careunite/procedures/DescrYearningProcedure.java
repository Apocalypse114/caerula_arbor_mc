package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrYearningProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.unripe_yearning").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.unripe_yearning.description_0").getString();
	}
}
