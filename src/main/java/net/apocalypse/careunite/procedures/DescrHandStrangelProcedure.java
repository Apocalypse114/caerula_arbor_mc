package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrHandStrangelProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.hand_of_strangle").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.hand_of_strangle.description_0").getString();
	}
}
