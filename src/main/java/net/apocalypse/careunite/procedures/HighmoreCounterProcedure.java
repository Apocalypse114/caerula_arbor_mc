package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.HighmoreShootEntity;
import net.apocalypse.careunite.entity.HighmoreEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class HighmoreCounterProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getSource(), event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, DamageSource damagesource, Entity entity, Entity sourceentity) {
		execute(null, world, damagesource, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, DamageSource damagesource, Entity entity, Entity sourceentity) {
		if (damagesource == null || entity == null || sourceentity == null)
			return;
		double range = 0;
		double atk = 0;
		if (entity instanceof HighmoreEntity) {
			if (entity instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(CaerulaArborModMobEffects.COOLDOWN_SINAL.get())) {
				return;
			}
			if (!(damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:hand_spike"))) || damagesource.is(DamageTypes.THORNS) || sourceentity instanceof HighmoreEntity)) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COOLDOWN_SINAL.get(), 100, 0, false, false));
				if ((entity instanceof HighmoreEntity _datEntI ? _datEntI.getEntityData().get(HighmoreEntity.DATA_phase) : 0) == 0) {
					range = 7;
				} else if ((entity instanceof HighmoreEntity _datEntI ? _datEntI.getEntityData().get(HighmoreEntity.DATA_phase) : 0) == 1) {
					range = 11;
				} else {
					range = 17;
				}
				if (!(null == sourceentity)) {
					if ((sourceentity != null ? entity.distanceTo(sourceentity) : -1) >= range) {
						atk = (entity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity10.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 2.5;
						if (Math.random() < 0.5) {
							if (world instanceof ServerLevel projectileLevel) {
								Projectile _entityToSpawn = new Object() {
									public Projectile getArrow(Level level, Entity shooter, float damage, int knockback, byte piercing) {
										AbstractArrow entityToSpawn = new HighmoreShootEntity(CaerulaArborModEntities.HIGHMORE_SHOOT.get(), level);
										entityToSpawn.setOwner(shooter);
										entityToSpawn.setBaseDamage(damage);
										entityToSpawn.setKnockback(knockback);
										entityToSpawn.setSilent(true);
										entityToSpawn.setPierceLevel(piercing);
										return entityToSpawn;
									}
								}.getArrow(projectileLevel, entity, (float) atk, 0, (byte) 1);
								_entityToSpawn.setPos((sourceentity.getX()), (sourceentity.getY() + sourceentity.getBbHeight() + 4), (sourceentity.getZ()));
								_entityToSpawn.shoot(0, (-1), 0, 2, 2);
								projectileLevel.addFreshEntity(_entityToSpawn);
							}
						}
						if (Math.random() < 0.5) {
							if (world instanceof ServerLevel projectileLevel) {
								Projectile _entityToSpawn = new Object() {
									public Projectile getArrow(Level level, Entity shooter, float damage, int knockback, byte piercing) {
										AbstractArrow entityToSpawn = new HighmoreShootEntity(CaerulaArborModEntities.HIGHMORE_SHOOT.get(), level);
										entityToSpawn.setOwner(shooter);
										entityToSpawn.setBaseDamage(damage);
										entityToSpawn.setKnockback(knockback);
										entityToSpawn.setSilent(true);
										entityToSpawn.setPierceLevel(piercing);
										return entityToSpawn;
									}
								}.getArrow(projectileLevel, entity, (float) atk, 0, (byte) 1);
								_entityToSpawn.setPos((sourceentity.getX() + sourceentity.getBbWidth() * 2), (sourceentity.getY() + sourceentity.getBbHeight()), (sourceentity.getZ()));
								_entityToSpawn.shoot((-1), 0, 0, 2, 2);
								projectileLevel.addFreshEntity(_entityToSpawn);
							}
						}
						if (Math.random() < 0.5) {
							if (world instanceof ServerLevel projectileLevel) {
								Projectile _entityToSpawn = new Object() {
									public Projectile getArrow(Level level, Entity shooter, float damage, int knockback, byte piercing) {
										AbstractArrow entityToSpawn = new HighmoreShootEntity(CaerulaArborModEntities.HIGHMORE_SHOOT.get(), level);
										entityToSpawn.setOwner(shooter);
										entityToSpawn.setBaseDamage(damage);
										entityToSpawn.setKnockback(knockback);
										entityToSpawn.setSilent(true);
										entityToSpawn.setPierceLevel(piercing);
										return entityToSpawn;
									}
								}.getArrow(projectileLevel, entity, (float) atk, 0, (byte) 1);
								_entityToSpawn.setPos((sourceentity.getX() - sourceentity.getBbWidth() * 2), (sourceentity.getY() + sourceentity.getBbHeight()), (sourceentity.getZ()));
								_entityToSpawn.shoot(1, 0, 0, 2, 2);
								projectileLevel.addFreshEntity(_entityToSpawn);
							}
						}
						if (Math.random() < 0.5) {
							if (world instanceof ServerLevel projectileLevel) {
								Projectile _entityToSpawn = new Object() {
									public Projectile getArrow(Level level, Entity shooter, float damage, int knockback, byte piercing) {
										AbstractArrow entityToSpawn = new HighmoreShootEntity(CaerulaArborModEntities.HIGHMORE_SHOOT.get(), level);
										entityToSpawn.setOwner(shooter);
										entityToSpawn.setBaseDamage(damage);
										entityToSpawn.setKnockback(knockback);
										entityToSpawn.setSilent(true);
										entityToSpawn.setPierceLevel(piercing);
										return entityToSpawn;
									}
								}.getArrow(projectileLevel, entity, (float) atk, 0, (byte) 1);
								_entityToSpawn.setPos((sourceentity.getX()), (sourceentity.getY() + sourceentity.getBbHeight()), (sourceentity.getZ() + sourceentity.getBbWidth() * 2));
								_entityToSpawn.shoot(0, 0, (-1), 2, 2);
								projectileLevel.addFreshEntity(_entityToSpawn);
							}
						}
						if (Math.random() < 0.5) {
							if (world instanceof ServerLevel projectileLevel) {
								Projectile _entityToSpawn = new Object() {
									public Projectile getArrow(Level level, Entity shooter, float damage, int knockback, byte piercing) {
										AbstractArrow entityToSpawn = new HighmoreShootEntity(CaerulaArborModEntities.HIGHMORE_SHOOT.get(), level);
										entityToSpawn.setOwner(shooter);
										entityToSpawn.setBaseDamage(damage);
										entityToSpawn.setKnockback(knockback);
										entityToSpawn.setSilent(true);
										entityToSpawn.setPierceLevel(piercing);
										return entityToSpawn;
									}
								}.getArrow(projectileLevel, entity, (float) atk, 0, (byte) 1);
								_entityToSpawn.setPos((sourceentity.getX()), (sourceentity.getY() + sourceentity.getBbHeight()), (sourceentity.getZ() - sourceentity.getBbWidth() * 2));
								_entityToSpawn.shoot(0, 0, 1, 2, 2);
								projectileLevel.addFreshEntity(_entityToSpawn);
							}
						}
					}
				}
			}
		}
	}
}
