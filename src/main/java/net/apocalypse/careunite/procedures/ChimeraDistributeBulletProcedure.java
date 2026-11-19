package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.TellerShotEntity;
import net.apocalypse.careunite.entity.FleefishBulletEntity;
import net.apocalypse.careunite.entity.FishSplashEntity;
import net.apocalypse.careunite.entity.FishShootEntity;
import net.apocalypse.careunite.entity.FakerggShootEntity;
import net.apocalypse.careunite.entity.AbandonedShootEntity;

public class ChimeraDistributeBulletProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double r = 0;
		double d = 0;
		double tx = 0;
		double tz = 0;
		double ty = 0;
		double dama = 0;
		dama = entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity0.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		for (int index0 = 0; index0 < 3; index0++) {
			r = Mth.nextDouble(RandomSource.create(), 0, 6.283);
			d = Mth.nextDouble(RandomSource.create(), 2, 9);
			tx = x + d * Math.cos(r);
			ty = y + Mth.nextDouble(RandomSource.create(), 8, 11);
			tz = z + d * Math.sin(r);
			if (world instanceof ServerLevel projectileLevel) {
				Projectile _entityToSpawn = new Object() {
					public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
						AbstractArrow entityToSpawn = new FishShootEntity(CaerulaArborModEntities.FISH_SHOOT.get(), level);
						entityToSpawn.setOwner(shooter);
						entityToSpawn.setBaseDamage(damage);
						entityToSpawn.setKnockback(knockback);
						entityToSpawn.setSilent(true);
						entityToSpawn.setCritArrow(true);
						return entityToSpawn;
					}
				}.getArrow(projectileLevel, entity, (float) dama, 0);
				_entityToSpawn.setPos(tx, ty, tz);
				_entityToSpawn.shoot(0, (-1), 0, 1, (float) 0.1);
				projectileLevel.addFreshEntity(_entityToSpawn);
			}
		}
		for (int index1 = 0; index1 < 3; index1++) {
			r = Mth.nextDouble(RandomSource.create(), 0, 6.283);
			d = Mth.nextDouble(RandomSource.create(), 2, 9);
			tx = x + d * Math.cos(r);
			ty = y + Mth.nextDouble(RandomSource.create(), 8, 11);
			tz = z + d * Math.sin(r);
			if (world instanceof ServerLevel projectileLevel) {
				Projectile _entityToSpawn = new Object() {
					public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
						AbstractArrow entityToSpawn = new FishSplashEntity(CaerulaArborModEntities.FISH_SPLASH.get(), level);
						entityToSpawn.setOwner(shooter);
						entityToSpawn.setBaseDamage(damage);
						entityToSpawn.setKnockback(knockback);
						entityToSpawn.setSilent(true);
						entityToSpawn.setCritArrow(true);
						return entityToSpawn;
					}
				}.getArrow(projectileLevel, entity, (float) dama, 0);
				_entityToSpawn.setPos(tx, ty, tz);
				_entityToSpawn.shoot(0, (-1), 0, 1, (float) 0.1);
				projectileLevel.addFreshEntity(_entityToSpawn);
			}
		}
		for (int index2 = 0; index2 < 2; index2++) {
			r = Mth.nextDouble(RandomSource.create(), 0, 6.283);
			d = Mth.nextDouble(RandomSource.create(), 2, 9);
			tx = x + d * Math.cos(r);
			ty = y + Mth.nextDouble(RandomSource.create(), 8, 11);
			tz = z + d * Math.sin(r);
			if (world instanceof ServerLevel projectileLevel) {
				Projectile _entityToSpawn = new Object() {
					public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
						AbstractArrow entityToSpawn = new FleefishBulletEntity(CaerulaArborModEntities.FLEEFISH_BULLET.get(), level);
						entityToSpawn.setOwner(shooter);
						entityToSpawn.setBaseDamage(damage);
						entityToSpawn.setKnockback(knockback);
						entityToSpawn.setSilent(true);
						entityToSpawn.setCritArrow(true);
						return entityToSpawn;
					}
				}.getArrow(projectileLevel, entity, (float) dama, 0);
				_entityToSpawn.setPos(tx, ty, tz);
				_entityToSpawn.shoot(0, (-1), 0, 1, (float) 0.1);
				projectileLevel.addFreshEntity(_entityToSpawn);
			}
		}
		for (int index3 = 0; index3 < 2; index3++) {
			r = Mth.nextDouble(RandomSource.create(), 0, 6.283);
			d = Mth.nextDouble(RandomSource.create(), 2, 9);
			tx = x + d * Math.cos(r);
			ty = y + Mth.nextDouble(RandomSource.create(), 8, 11);
			tz = z + d * Math.sin(r);
			if (world instanceof ServerLevel projectileLevel) {
				Projectile _entityToSpawn = new Object() {
					public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
						AbstractArrow entityToSpawn = new TellerShotEntity(CaerulaArborModEntities.TELLER_SHOT.get(), level);
						entityToSpawn.setOwner(shooter);
						entityToSpawn.setBaseDamage(damage);
						entityToSpawn.setKnockback(knockback);
						entityToSpawn.setSilent(true);
						entityToSpawn.setCritArrow(true);
						return entityToSpawn;
					}
				}.getArrow(projectileLevel, entity, (float) dama, 0);
				_entityToSpawn.setPos(tx, ty, tz);
				_entityToSpawn.shoot(0, (-1), 0, 1, (float) 0.1);
				projectileLevel.addFreshEntity(_entityToSpawn);
			}
		}
		for (int index4 = 0; index4 < 2; index4++) {
			r = Mth.nextDouble(RandomSource.create(), 0, 6.283);
			d = Mth.nextDouble(RandomSource.create(), 2, 9);
			tx = x + d * Math.cos(r);
			ty = y + Mth.nextDouble(RandomSource.create(), 8, 11);
			tz = z + d * Math.sin(r);
			if (world instanceof ServerLevel projectileLevel) {
				Projectile _entityToSpawn = new Object() {
					public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
						AbstractArrow entityToSpawn = new AbandonedShootEntity(CaerulaArborModEntities.ABANDONED_SHOOT.get(), level);
						entityToSpawn.setOwner(shooter);
						entityToSpawn.setBaseDamage(damage);
						entityToSpawn.setKnockback(knockback);
						entityToSpawn.setSilent(true);
						entityToSpawn.setCritArrow(true);
						return entityToSpawn;
					}
				}.getArrow(projectileLevel, entity, (float) dama, 0);
				_entityToSpawn.setPos(tx, ty, tz);
				_entityToSpawn.shoot(0, (-1), 0, 1, (float) 0.1);
				projectileLevel.addFreshEntity(_entityToSpawn);
			}
		}
		for (int index5 = 0; index5 < 3; index5++) {
			r = Mth.nextDouble(RandomSource.create(), 0, 6.283);
			d = Mth.nextDouble(RandomSource.create(), 2, 9);
			tx = x + d * Math.cos(r);
			ty = y + Mth.nextDouble(RandomSource.create(), 8, 11);
			tz = z + d * Math.sin(r);
			if (world instanceof ServerLevel projectileLevel) {
				Projectile _entityToSpawn = new Object() {
					public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
						AbstractArrow entityToSpawn = new FakerggShootEntity(CaerulaArborModEntities.FAKERGG_SHOOT.get(), level);
						entityToSpawn.setOwner(shooter);
						entityToSpawn.setBaseDamage(damage);
						entityToSpawn.setKnockback(knockback);
						entityToSpawn.setSilent(true);
						entityToSpawn.setCritArrow(true);
						return entityToSpawn;
					}
				}.getArrow(projectileLevel, entity, (float) dama, 0);
				_entityToSpawn.setPos(tx, ty, tz);
				_entityToSpawn.shoot(0, (-1), 0, 1, (float) 0.1);
				projectileLevel.addFreshEntity(_entityToSpawn);
			}
		}
	}
}
