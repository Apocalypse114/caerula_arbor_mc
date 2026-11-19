package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

public class IsNodeSelectedProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		String title = "";
		title = entity.getPersistentData().getString("showcasingEvoNode");
		return !(title).isEmpty();
	}
}
