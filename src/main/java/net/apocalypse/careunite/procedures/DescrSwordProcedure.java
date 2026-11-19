package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrSwordProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.hand_sword").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.hand_sword.description_0").getString();
	}
}
