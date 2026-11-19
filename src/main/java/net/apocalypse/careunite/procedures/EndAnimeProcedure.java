package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.TideDeathrepellerEntity;
import net.apocalypse.careunite.entity.TideBishopEntity;

public class EndAnimeProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof TideBishopEntity) {
			((TideBishopEntity) entity).setAnimation("animation.tidebishop.die_idle");
		}
		if (entity instanceof TideDeathrepellerEntity) {
			((TideDeathrepellerEntity) entity).setAnimation("animation.deathrepeller.die_idle");
		}
		entity.setShiftKeyDown(false);
	}
}
