package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrGlowbodyProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.relic_cursed_glowbody").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.relic_cursed_glowbody.description_0").getString();
	}
}
