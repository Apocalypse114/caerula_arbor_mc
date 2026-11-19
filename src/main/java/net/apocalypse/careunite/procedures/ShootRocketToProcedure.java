package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.MonstrosityRocketEntity;
import net.apocalypse.careunite.entity.IberiaMonstrosityEntity;
import net.minecraft.world.phys.Vec3;

public class ShootRocketToProcedure {
	public static Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
		AbstractArrow entityToSpawn = new MonstrosityRocketEntity(CaerulaArborModEntities.MONSTROSITY_ROCKET.get(), level);
		entityToSpawn.setOwner(shooter);
		entityToSpawn.setBaseDamage(damage);
		entityToSpawn.setKnockback(knockback);
		entityToSpawn.setSilent(true);
		return entityToSpawn;
	}

	
	public static void execute(Entity entity, Entity target) {
		if (entity == null)
			return;
		if (entity instanceof IberiaMonstrosityEntity _shootFrom){
			double dada = _shootFrom.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
			Level projectileLevel = _shootFrom.level();
			if (!projectileLevel.isClientSide()) {
				Projectile _entityToSpawn = getArrow(projectileLevel, entity, 5, 0);
				if (_entityToSpawn instanceof MonstrosityRocketEntity rocket){
					_entityToSpawn.setPos(entity.position().add(0, 2.375, 0));
					rocket.setRocketDamage(dada);
					rocket.setRocketTarget(target);
					_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1.5f, 25);
					projectileLevel.addFreshEntity(_entityToSpawn);
				}
			}
		}
	}
}
