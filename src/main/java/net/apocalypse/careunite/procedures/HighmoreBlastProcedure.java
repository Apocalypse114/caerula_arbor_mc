package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.HighmoreShootEntity;
import net.apocalypse.careunite.entity.HighmoreEntity;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class HighmoreBlastProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(48 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator == entity) {
					continue;
				}
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
					if (!(entityiterator == (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null))) {
						continue;
					}
				}
				if (!(entityiterator instanceof Mob) && !(entityiterator instanceof Player)) {
					continue;
				}
				if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 24) {
					new Object() {
						void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
							entityiterator.hurt(
									new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:highmore_attack"))), entity),
									(float) ((entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity8.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0)
											* (3.5 + (entity instanceof HighmoreEntity _datEntI ? _datEntI.getEntityData().get(HighmoreEntity.DATA_phase) : 0))));
							final int tick2 = ticks;
							CaerulaArborMod.queueServerWork(tick2, () -> {
								if (timedlooptotal > timedloopiterator + 1) {
									timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
								}
							});
						}
					}.timedLoop(0, (int) (2 + (entity instanceof HighmoreEntity _datEntI ? _datEntI.getEntityData().get(HighmoreEntity.DATA_phase) : 0)), 1);
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 40, 0));
				}
			}
		}
		for (int index0 = 0; index0 < 120; index0++) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.ENCHANTED_HIT, (x + 24 * Math.sin(Math.toRadians(3 * index0))), y, (z + 24 * Math.cos(Math.toRadians(3 * index0))), 6, 0.15, 0.15, 0.15, 0.1);
		}
		new Object() {
			void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
				for (int index1 = 0; index1 < 12; index1++) {
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
								(float) ((entity instanceof LivingEntity _livingEntity17 && _livingEntity17.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity17.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 3.3), 0);
						_entityToSpawn.setPos(x, (y + 0.75), z);
						_entityToSpawn.shoot(Math.sin(30 * index1), 0, Math.cos(30 * index1), 1, 0);
						projectileLevel.addFreshEntity(_entityToSpawn);
					}
				}
				final int tick2 = ticks;
				CaerulaArborMod.queueServerWork(tick2, () -> {
					if (timedlooptotal > timedloopiterator + 1) {
						timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
					}
				});
			}
		}.timedLoop(0, (int) (1 + (entity instanceof HighmoreEntity _datEntI ? _datEntI.getEntityData().get(HighmoreEntity.DATA_phase) : 0)), 3);
	}
}
