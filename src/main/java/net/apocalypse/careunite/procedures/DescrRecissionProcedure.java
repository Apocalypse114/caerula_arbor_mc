package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrRecissionProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.rescission").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.rescission.description_0").getString();
	}
}
