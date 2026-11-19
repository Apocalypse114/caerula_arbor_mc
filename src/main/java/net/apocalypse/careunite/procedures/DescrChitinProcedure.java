package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrChitinProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.chitin_knife").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.chitin_knife.description_0").getString();
	}
}
