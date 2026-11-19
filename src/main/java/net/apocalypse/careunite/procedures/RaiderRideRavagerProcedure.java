package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;

public class RaiderRideRavagerProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity ravager = null;
		boolean canOrWillRide = false;
		if (!entity.isAlive()) {
			return;
		}
		if (entity.isPassenger()) {
			return;
		}
		if (entity.tickCount % 40 == 10 && Math.random() < 0.33) {
			ravager = NearestRidableRavagerProcedure.execute(world, x, y, z, entity, 12);
			if (!(ravager == null) && ravager.isAlive()) {
				if (!ravager.isVehicle()) {
					if (entity instanceof Mob _entity)
						_entity.getNavigation().moveTo((ravager.getX()), (ravager.getY()), (ravager.getZ()), 1);
					canOrWillRide = true;
				}
			}
		}
		if (entity.tickCount % 20 == 10) {
			ravager = NearestRidableRavagerProcedure.execute(world, x, y, z, entity, 3);
			if (!(ravager == null) && ravager.isAlive()) {
				if (ravager.isVehicle()) {
					return;
				}
				entity.startRiding(ravager);
				canOrWillRide = true;
			}
		}
		if (!canOrWillRide) {
			SeabornRidePolarProcedure.execute(world, x, y, z, entity);
		}
	}
}
