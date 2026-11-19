package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.ChitinGolemEntity;

public class ChitinGolemTextureProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double perc = 0;
		perc = (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) / (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
		if (perc < 0.25) {
			if (entity instanceof ChitinGolemEntity animatable)
				animatable.setTexture("chitin_golem_3");
		} else if (perc < 0.5) {
			if (entity instanceof ChitinGolemEntity animatable)
				animatable.setTexture("chitin_golem_2");
		} else if (perc < 0.75) {
			if (entity instanceof ChitinGolemEntity animatable)
				animatable.setTexture("chitin_golem_1");
		} else {
			if (entity instanceof ChitinGolemEntity animatable)
				animatable.setTexture("chitin_golem_0");
		}
	}
}
