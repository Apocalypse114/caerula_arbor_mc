package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.TideDeathrepellerEntity;
import net.apocalypse.careunite.entity.TellerShotEntity;

import java.util.Comparator;

public class BishopHealProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double sklp = 0;
		Entity rep = null;
		if (entity.isAlive() && (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
			{
				Entity _shootFrom = entity;
				Level projectileLevel = _shootFrom.level();
				if (!projectileLevel.isClientSide()) {
					Projectile _entityToSpawn = new Object() {
						public Projectile getArrow(Level level, Entity shooter, float damage, int knockback, byte piercing) {
							AbstractArrow entityToSpawn = new TellerShotEntity(CaerulaArborModEntities.TELLER_SHOT.get(), level);
							entityToSpawn.setOwner(shooter);
							entityToSpawn.setBaseDamage(damage);
							entityToSpawn.setKnockback(knockback);
							entityToSpawn.setSilent(true);
							entityToSpawn.setPierceLevel(piercing);
							entityToSpawn.setCritArrow(true);
							return entityToSpawn;
						}
					}.getArrow(projectileLevel, entity,
							(float) (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity3.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0), 0, (byte) 1);
					_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
					_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, (float) 1.5, 0);
					projectileLevel.addFreshEntity(_entityToSpawn);
				}
			}
			if (entity instanceof LivingEntity _entity)
				_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.1));
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, (y + 1.5), z, 64, 1.5, 1.5, 1.5, 0.2);
		}
		rep = (Entity) world.getEntitiesOfClass(TideDeathrepellerEntity.class, AABB.ofSize(new Vec3(x, y, z), 96, 96, 96), e -> true).stream().sorted(new Object() {
			Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
				return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
			}
		}.compareDistOf(x, y, z)).findFirst().orElse(null);
		if (!(rep == null) && rep.isAlive() && (rep instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (rep instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
			if (rep instanceof LivingEntity _entity)
				_entity.setHealth((float) ((rep instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + (rep instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.1));
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.HAPPY_VILLAGER, (rep.getX()), (rep.getY() + 1.5), (rep.getZ()), 64, 1.5, 1.5, 1.5, 0.2);
		}
	}
}
