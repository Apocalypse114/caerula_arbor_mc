package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrOrangeProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.golden_storm").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.golden_storm.description_0").getString();
	}
}
