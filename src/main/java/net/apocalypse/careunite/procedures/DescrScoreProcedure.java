package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrScoreProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.score").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.score.description_0").getString();
	}
}
