package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

public class ValerioPunishProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z, Entity entity, double r) {
		if (entity == null)
			return false;
		double num = 0;
		for (Entity entityiterator : world.getEntities(entity, new AABB((x + r), (y + 8), (z + r), (x - r), (y - 6), (z - r)))) {
			if (IsValidEnemyForAlchemyProcedure.execute(entityiterator, entity)) {
				if ((entityiterator instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= 0 && !entityiterator.getPersistentData().getBoolean("valerioPunished")) {
					entityiterator.getPersistentData().putBoolean("valerioPunished", true);
					DeployInquisitionFIreAtProcedure.execute(world, entityiterator.getX(), entityiterator.getY(), entityiterator.getZ(), entity, 10);
					num++;
				}
			}
			if (num >= 4) {
				break;
			}
		}
		return num > 0;
	}
}
