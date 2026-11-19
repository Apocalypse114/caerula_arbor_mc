package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrHeartProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.caerula_heart").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.caerula_heart.description_0").getString();
	}
}
