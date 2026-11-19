package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrAllayProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.allay_sculpture").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.allay_sculpture.description_0").getString();
	}
}
