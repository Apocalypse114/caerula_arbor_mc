package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrHemostProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.smelly_hemostatic").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.smelly_hemostatic.description_0").getString();
	}
}
