package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrLongevityProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.proof_of_longevity").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.proof_of_longevity.description_0").getString();
	}
}
