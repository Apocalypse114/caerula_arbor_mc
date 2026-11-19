package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.HighmoreEntity;

public class HighmoreInitProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof HighmoreEntity) {
			((HighmoreEntity) entity).setAnimation("animation.highmore.start");
		}
	}
}
