package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.FleefishBulletEntity;

public class DrifterBallDispenserProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Direction direction) {
		if (direction == null)
			return;
		double xx = 0;
		double yy = 0;
		double zz = 0;
		xx = x + 0.5 + direction.getStepX() * 0.625;
		yy = y + 0.5 + direction.getStepY() * 0.625;
		zz = z + 0.5 + direction.getStepZ() * 0.625;
		if (!(direction == Direction.DOWN || direction == Direction.UP)) {
			yy = yy - 0.125;
		}
		if (world instanceof ServerLevel projectileLevel) {
			Projectile _entityToSpawn = new Object() {
				public Projectile getArrow(Level level, float damage, int knockback) {
					AbstractArrow entityToSpawn = new FleefishBulletEntity(CaerulaArborModEntities.FLEEFISH_BULLET.get(), level);
					entityToSpawn.setBaseDamage(damage);
					entityToSpawn.setKnockback(knockback);
					entityToSpawn.setSilent(true);
					return entityToSpawn;
				}
			}.getArrow(projectileLevel, 5, 0);
			_entityToSpawn.setPos(xx, yy, zz);
			_entityToSpawn.shoot((direction.getStepX()), (direction.getStepY()), (direction.getStepZ()), (float) 1.5, 1);
			projectileLevel.addFreshEntity(_entityToSpawn);
		}
	}
}
