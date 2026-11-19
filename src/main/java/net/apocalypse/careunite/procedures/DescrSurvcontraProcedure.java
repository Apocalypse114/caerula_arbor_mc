package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrSurvcontraProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.survivor_contract").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.survivor_contract.description_0").getString();
	}
}
