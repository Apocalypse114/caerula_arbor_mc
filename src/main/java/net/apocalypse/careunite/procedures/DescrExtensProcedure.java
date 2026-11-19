package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrExtensProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.kings_extension").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.kings_extension.description_0").getString();
	}
}
