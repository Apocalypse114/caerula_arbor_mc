package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrStareProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.guardian_stare").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.guardian_stare.description_0").getString();
	}
}
