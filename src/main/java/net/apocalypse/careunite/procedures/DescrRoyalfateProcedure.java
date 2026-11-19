package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrRoyalfateProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.royal_fate").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.royal_fate.description_0").getString();
	}
}
