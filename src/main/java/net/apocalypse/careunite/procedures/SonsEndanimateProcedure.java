package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.SonsEntity;

public class SonsEndanimateProcedure {
	public static void execute(Entity sourceentity) {
		if (sourceentity == null)
			return;
		if (sourceentity instanceof SonsEntity) {
			((SonsEntity) sourceentity).setAnimation("animation.bishopson.end");
		}
	}
}
