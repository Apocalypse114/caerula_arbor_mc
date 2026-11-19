package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrBedProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.archfiends_bed").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.archfiends_bed.description_0").getString();
	}
}
