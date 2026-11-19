package net.apocalypse.careunite.procedures;

import net.minecraft.network.chat.Component;

public class EchoNameFromIndexProcedure {
	public static String execute(double id) {
		double index = 0;
		String name = "";
		index = (int) id;
		if (index == 0) {
			name = "explore";
		} else if (index == 1) {
			name = "fight";
		} else if (index == 2) {
			name = "remould";
		} else if (index == 3) {
			name = "vissis";
		} else if (index == 4) {
			name = "feed";
		} else if (index == 5) {
			name = "crowd";
		} else if (index == 6) {
			name = "spindling";
		} else if (index == 7) {
			name = "adapt";
		}
		return Component.translatable(("gui.caerula_arbor_reunite.echo_show.label_echo_" + name)).getString();
	}
}
