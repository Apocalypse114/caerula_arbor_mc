package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class DescrMusicboxProcedure {
	public static String execute() {
		return Component.translatable("item.caerula_arbor_reunite.solo_music_box").getString() + ": " + Component.translatable("item.caerula_arbor_reunite.solo_music_box.description_0").getString();
	}
}
