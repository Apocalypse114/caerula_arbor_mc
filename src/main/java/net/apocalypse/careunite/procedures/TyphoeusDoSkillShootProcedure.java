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
import net.apocalypse.careunite.init.CaerulaArborModSounds;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.ShotOceanArrowEntity;
import net.apocalypse.careunite.entity.LargeArrowEntity;
import net.minecraft.sounds.SoundSource;

public class TyphoeusDoSkillShootProcedure {
	public static Projectile getArrow0(Level level, Entity shooter, float damage, int knockback, byte piercing) {
		AbstractArrow entityToSpawn = new LargeArrowEntity(CaerulaArborModEntities.LARGE_ARROW.get(), level);
		entityToSpawn.setOwner(shooter);
		entityToSpawn.setBaseDamage(damage);
		entityToSpawn.setKnockback(knockback);
		entityToSpawn.setCritArrow(true);
		//entityToSpawn.setSilent(true);
		entityToSpawn.setPierceLevel(piercing);
		//entityToSpawn.setNoGravity(true);
		return entityToSpawn;
	}

	public static Projectile getArrow1(Level level, Entity shooter, float damage, int knockback) {
		AbstractArrow entityToSpawn = new ShotOceanArrowEntity(CaerulaArborModEntities.SHOT_OCEAN_ARROW.get(), level);
		entityToSpawn.setOwner(shooter);
		entityToSpawn.setBaseDamage(damage);
		entityToSpawn.setKnockback(knockback);
		entityToSpawn.setSilent(true);
		return entityToSpawn;
	}

	public static void execute(LevelAccessor world, Entity center, Entity entity, double dx, double dz) {
		if (center == null || entity == null)
			return;
		double tx = center.getX();
		double ty = center.getY();
		double tz = center.getZ();
		entity.level().playSound(null, tx, ty, tz, 
			CaerulaArborModSounds.TYPHEOUS_SKILL_DESC.get(), 
			SoundSource.NEUTRAL, 1, 1f / (world.getRandom().nextFloat() * 0.5f + 1));
		float atk = (float) (entity instanceof LivingEntity _livingEntity4 
			&& _livingEntity4.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? 
			_livingEntity4.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0);
		double deltaH = Mth.nextDouble(world.getRandom(), 6, 10);
		if (world instanceof ServerLevel projectileLevel) {
			Projectile _entityToSpawn = getArrow0(projectileLevel, entity, atk * 0.8f, 0, (byte) 6);
			_entityToSpawn.setPos((tx + dx), (ty + deltaH), (tz + dz));
			_entityToSpawn.shoot((dx * (-1)), (deltaH * (-1)), (dz * (-1)), 2, 0);
			projectileLevel.addFreshEntity(_entityToSpawn);
			for (int index0 = 0; index0 < 9; index0++) {
				for (int index1 = 0; index1 < 9; index1++) {
					if (!(index0 == 4 && index1 == 4)) {
						_entityToSpawn = getArrow1(projectileLevel, entity,
 atk * 0.3f, 0);
						_entityToSpawn.setPos((tx + dx + index0 - 4), (ty + deltaH), (tz + dz + index1 - 4));
						_entityToSpawn.shoot((dx * (-1)), (deltaH * (-1)), (dz * (-1)), 2, 0);
						projectileLevel.addFreshEntity(_entityToSpawn);
					}
				}
			}
		}
	}
}
