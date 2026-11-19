package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

public class GetNodeTitleProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		String title = "";
		title = entity.getPersistentData().getString("showcasingEvoNode");
		if ((title).isEmpty()) {
			title = "empty";
		}
		return Component.translatable(("p_evo.caerula_arbor_reunite." + title)).getString();
	}
}
