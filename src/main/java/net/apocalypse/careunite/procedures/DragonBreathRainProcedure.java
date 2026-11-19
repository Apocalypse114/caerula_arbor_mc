package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;

public class DragonBreathRainProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double r = 0;
		double d = 0;
		double tx = 0;
		double tz = 0;
		r = Mth.nextDouble(RandomSource.create(), 0, 6.283);
		d = Mth.nextDouble(RandomSource.create(), 3, 8);
		tx = x + d * Math.cos(r);
		tz = z + d * Math.sin(r);
		if (world instanceof ServerLevel projectileLevel) {
			Projectile _entityToSpawn = new Object() {
				public Projectile getFireball(Level level, Entity shooter, double ax, double ay, double az) {
					AbstractHurtingProjectile entityToSpawn = new DragonFireball(EntityType.DRAGON_FIREBALL, level);
					entityToSpawn.setOwner(shooter);
					entityToSpawn.xPower = ax;
					entityToSpawn.yPower = ay;
					entityToSpawn.zPower = az;
					return entityToSpawn;
				}
			}.getFireball(projectileLevel, entity, 0, (-0.1), 0);
			_entityToSpawn.setPos(tx, (y + Mth.nextInt(RandomSource.create(), 6, 9)), tz);
			_entityToSpawn.shoot(0, 1, 0, (float) (-0.5), 0);
			projectileLevel.addFreshEntity(_entityToSpawn);
		}
	}
}
