package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DecrSpearProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.kings_spear").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.kings_spear.description_0").getString();
	}
}
