package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

public class G3tNodeDesc1Procedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		String title = "";
		String key = "";
		title = entity.getPersistentData().getString("showcasingEvoNode");
		if ((title).isEmpty()) {
			title = "empty";
		}
		key = "p_evo.caerula_arbor_reunite." + title + ".desc1";
		title = Component.translatable(key).getString();
		if ((title).equals(key)) {
			return "";
		}
		return title;
	}
}
