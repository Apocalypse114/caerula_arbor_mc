package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.FishSplashEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class AttackAllonTrailProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double num = 0;
		double rand = 0;
		double dama = 0;
		num = 0;
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(CaerulaArborModMobEffects.TRAIL_BUFF.get())) {
			dama = (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity1.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 0.5;
			for (Entity entityiterator : world.getEntities(entity, new AABB((x + 48), (y + 6), (z + 48), (x - 48), (y - 6), (z - 48)))) {
				if (entityiterator instanceof Player && (entityiterator.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_oceanization >= 3) {
					continue;
				}
				if (IsValidEnemyForSeabornProcedure.execute(entityiterator, entity) && entityiterator instanceof LivingEntity _livEnt3 && _livEnt3.hasEffect(CaerulaArborModMobEffects.TRAIL_BUFF.get())) {
					rand = Mth.nextDouble(RandomSource.create(), 7, 11);
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
						_entityToSpawn.setPos((entityiterator.getX()), (entityiterator.getY() + rand), (entityiterator.getZ()));
						_entityToSpawn.shoot(0, (-1), 0, (float) 1.5, 0);
						projectileLevel.addFreshEntity(_entityToSpawn);
					}
					num = num + 1;
					new Object() {
						void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
							if (world instanceof ServerLevel _level)
								_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.SEA_SPLASH.get()), (entityiterator.getX() + ((x - entityiterator.getX()) / 40) * timedloopiterator),
										(entityiterator.getY() + 9 + ((y - (entityiterator.getY() + 9)) / 40) * timedloopiterator), (entityiterator.getZ() + ((z - entityiterator.getZ()) / 40) * timedloopiterator), 1, 0.1, 0.1, 0.1, 0.01);
							final int tick2 = ticks;
							CaerulaArborMod.queueServerWork(tick2, () -> {
								if (timedlooptotal > timedloopiterator + 1) {
									timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
								}
							});
						}
					}.timedLoop(0, 40, 1);
				}
				if (num >= 3) {
					break;
				}
			}
		}
	}
}
