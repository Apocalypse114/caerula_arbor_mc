package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrRainbowProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.rainbow_candy").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.rainbow_candy.description_0").getString();
	}
}
