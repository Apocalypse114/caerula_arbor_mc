package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrKettleProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.kettle").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.kettle.description_0").getString();
	}
}
