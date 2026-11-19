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
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.HighmoreShootEntity;

public class HighmoreRangedAttackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double angl = 0;
		double rng = 0;
		angl = Mth.nextDouble(RandomSource.create(), 0, 6.283);
		rng = Mth.nextDouble(RandomSource.create(), 5, 17);
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.END_ROD, x, (y + 1), z, 72, 2, 2, 2, 0.2);
		if (world instanceof ServerLevel projectileLevel) {
			Projectile _entityToSpawn = new Object() {
				public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
					AbstractArrow entityToSpawn = new HighmoreShootEntity(CaerulaArborModEntities.HIGHMORE_SHOOT.get(), level);
					entityToSpawn.setOwner(shooter);
					entityToSpawn.setBaseDamage(damage);
					entityToSpawn.setKnockback(knockback);
					entityToSpawn.setSilent(true);
					return entityToSpawn;
				}
			}.getArrow(projectileLevel, entity,
					(float) ((entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity3.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 1.5), 0);
			_entityToSpawn.setPos(x, (y + 9), z);
			_entityToSpawn.shoot((rng * Math.sin(angl)), (-9), (rng * Math.cos(angl)), (float) 1.5, 5);
			projectileLevel.addFreshEntity(_entityToSpawn);
		}
		for (int index0 = 0; index0 < 120; index0++) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.ENCHANTED_HIT, (x + 24 * Math.sin(Math.toRadians(3 * index0))), y, (z + 24 * Math.cos(Math.toRadians(3 * index0))), 3, 0.15, 0.15, 0.15, 0.1);
		}
	}
}
