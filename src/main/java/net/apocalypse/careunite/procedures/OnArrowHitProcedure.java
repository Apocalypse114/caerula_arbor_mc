package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.ShotOceanArrowEntity;
import net.apocalypse.careunite.entity.LargeArrowEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class OnArrowHitProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getSource(), event.getEntity(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity, Entity sourceentity, double amount) {
		execute(null, world, x, y, z, damagesource, entity, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity, Entity sourceentity, double amount) {
		if (damagesource == null || entity == null || sourceentity == null)
			return;
		Entity arrow = null;
		double yaw = 0;
		double sine = 0;
		double cosine = 0;
		double vx = 0;
		double vz = 0;
		double lll = 0;
		arrow = damagesource.getDirectEntity();
		if (arrow instanceof Arrow || arrow instanceof ShotOceanArrowEntity || arrow instanceof LargeArrowEntity) {
			if (arrow.getPersistentData().getBoolean("ComplexChitin")) {
				DeductPlayerSanityProcedure.execute(entity, amount * 4);
				for (int index0 = 0; index0 < 3; index0++) {
					yaw = Mth.nextInt(RandomSource.create(), -30, 30);
					sine = Math.sin(Math.toRadians(yaw));
					cosine = Math.cos(Math.toRadians(yaw));
					vx = arrow.getDeltaMovement().x();
					vz = arrow.getDeltaMovement().z();
					if (world instanceof ServerLevel projectileLevel) {
						Projectile _entityToSpawn = new Object() {
							public Projectile getArrow(Level level, Entity shooter, float damage, int knockback, byte piercing) {
								AbstractArrow entityToSpawn = new Arrow(EntityType.ARROW, level);
								entityToSpawn.setOwner(shooter);
								entityToSpawn.setBaseDamage(damage);
								entityToSpawn.setKnockback(knockback);
								entityToSpawn.setPierceLevel(piercing);
								entityToSpawn.setCritArrow(true);
								entityToSpawn.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
								return entityToSpawn;
							}
						}.getArrow(projectileLevel, (damagesource.getEntity()), (float) (amount * 0.64), 0, (byte) 1);
						_entityToSpawn.setPos(x, (arrow.getY()), z);
						_entityToSpawn.shoot((vx * cosine + vz * sine), (arrow.getDeltaMovement().y()), (vz * cosine - vx * sine), (float) 1.5, (float) 0.05);
						projectileLevel.addFreshEntity(_entityToSpawn);
					}
				}
			}
			lll = arrow.getPersistentData().getDouble("TrailriteLink");
			if (lll > 0) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COOLDOWN_SINAL.get(), 20, 0, false, false));
				TrailroiteArrowFuncProcedure.execute(world, x, entity.getY(), z, arrow, damagesource.getEntity(), amount, lll);
				if (lll > 4) {
					if (world instanceof ServerLevel _level)
						_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.MOIST_BOOM.get()), x, (y + 0.5), z, 2, 0.1, 0.1, 0.1, 0.1);
					DeductPlayerSanityProcedure.execute(entity, amount * 5);
					GiveLessArmorProcedure.execute(entity, 16);
				}
			}
			if (damagesource.is(DamageTypes.ARROW)) {
				if (arrow.getPersistentData().getBoolean("ComplexChitinCB")) {
					if (arrow.getPersistentData().getBoolean("ComplexChitinCB_ocean")) {
						ComplChitinArrowRangedProcedure.execute(world, x, y, z, entity, arrow, sourceentity, amount, 4);
					} else {
						ComplChitinArrowRangedProcedure.execute(world, x, y, z, entity, arrow, sourceentity, amount, 2.5);
					}
					entity.invulnerableTime = 0;
				}
				if (arrow.getPersistentData().getBoolean("TrailriteCB")) {
					if (arrow.getPersistentData().getBoolean("TrailriteCB_alloy")) {
						if (arrow.getPersistentData().getBoolean("TrailriteCB_ew")) {
							MortarFireExplodeProcedure.execute(world, x, y, z, sourceentity, 12, 2, 2);
						}
						TrailriteArrowRangedProcedure.execute(world, x, y, z, entity, arrow, sourceentity, amount, 7);
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ROCK_BREAK.get(), 80, 0, false, false));
					} else if (arrow.getPersistentData().getBoolean("TrailriteCB_ocean")) {
						ComplChitinArrowRangedProcedure.execute(world, x, y, z, entity, arrow, sourceentity, amount, 5);
					} else {
						ComplChitinArrowRangedProcedure.execute(world, x, y, z, entity, arrow, sourceentity, amount, 2.75);
					}
					entity.invulnerableTime = 0;
				}
			}
		}
	}
}
