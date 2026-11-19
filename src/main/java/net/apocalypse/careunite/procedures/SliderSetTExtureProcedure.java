package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.SliderFishEntity;

public class SliderSetTExtureProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		String name = "";
		name = entity.getDisplayName().getString();
		if ((name).equals("\u5B89\u59AE\u5854") || (name).equals("Anneta")) {
			if (entity instanceof SliderFishEntity animatable)
				animatable.setTexture("slider_with_box");
		}
	}
}
