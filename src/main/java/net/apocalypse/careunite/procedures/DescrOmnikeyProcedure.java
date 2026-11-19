package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrOmnikeyProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.omni_key").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.omni_key.description_0").getString();
	}
}
