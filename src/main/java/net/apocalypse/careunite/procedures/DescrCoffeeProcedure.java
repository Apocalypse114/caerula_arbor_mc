package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrCoffeeProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.coffee_candy").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.coffee_candy.description_0").getString();
	}
}
