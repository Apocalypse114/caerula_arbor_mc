package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrToponymProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.toponym_textology").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.toponym_textology.description_0").getString();
	}
}
