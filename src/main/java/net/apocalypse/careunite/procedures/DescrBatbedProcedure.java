package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrBatbedProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.bat_bed").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.bat_bed.description_0").getString();
	}
}
