package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrCrownProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.relic_crown").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.relic_crown.description_0").getString();
	}
}
