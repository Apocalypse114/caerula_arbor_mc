package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.HighmoreShootEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class HighmoreMutltiShootProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity target, double t) {
		if (entity == null || target == null)
			return;
		new Object() {
			void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
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
							(float) ((entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity4.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 0.75), 0);
					_entityToSpawn.setPos(x, (y + 1.5), z);
					_entityToSpawn.shoot((target.getX() - x), ((target.getY() + target.getBbHeight() * 0.5) - (y + 2.5)), (target.getZ() - z), (float) 1.5, 5);
					projectileLevel.addFreshEntity(_entityToSpawn);
				}
				final int tick2 = ticks;
				CaerulaArborMod.queueServerWork(tick2, () -> {
					if (timedlooptotal > timedloopiterator + 1) {
						timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
					}
				});
			}
		}.timedLoop(0, (int) t, 3);
	}
}
