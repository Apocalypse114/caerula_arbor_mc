package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrHandsSwipeProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.hand_of_spotless").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.hand_of_spotless.description_0").getString();
	}
}
