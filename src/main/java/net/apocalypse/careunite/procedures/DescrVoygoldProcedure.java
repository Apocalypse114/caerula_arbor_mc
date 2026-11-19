package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrVoygoldProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.voyage_of_gold").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.voyage_of_gold.description_0").getString();
	}
}
