package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Entity;

public class CrystalTicksProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (!entity.isAlive()) {
			entity.setDeltaMovement(new Vec3(0, 0, 0));
			return;
		}
		if (entity.tickCount < 5) {
			entity.setDeltaMovement(new Vec3(0, 0, 0));
		}
		entity.clearFire();
		entity.setAirSupply(20);
	}
}
