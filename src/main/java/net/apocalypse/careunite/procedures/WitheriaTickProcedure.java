package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.OceannizedWitheriaEntity;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class WitheriaTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		boolean shelled = false;
		double spawn = 0;
		double skillp = 0;
		double duration = 0;
		double idle = 0;
		if (!entity.isAlive()) {
			return;
		}
		skillp = entity instanceof OceannizedWitheriaEntity _datEntI ? _datEntI.getEntityData().get(OceannizedWitheriaEntity.DATA_skillp) : 0;
		duration = entity instanceof OceannizedWitheriaEntity _datEntI ? _datEntI.getEntityData().get(OceannizedWitheriaEntity.DATA_duration) : 0;
		if (duration > 0) {
			if (entity instanceof OceannizedWitheriaEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceannizedWitheriaEntity.DATA_duration, (int) (duration - 1));
		}
		if ((entity.getDisplayName().getString()).equals(entity.getType().getDescription().getString())) {
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			idle = entity instanceof OceannizedWitheriaEntity _datEntI ? _datEntI.getEntityData().get(OceannizedWitheriaEntity.DATA_idle_time) : 0;
			if (enemy == null || !enemy.isAlive()) {
				if (entity instanceof OceannizedWitheriaEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceannizedWitheriaEntity.DATA_idle_time, (int) (idle + 1));
			} else {
				if (entity instanceof OceannizedWitheriaEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceannizedWitheriaEntity.DATA_idle_time, 0);
			}
		}
		if (skillp > 0) {
			if (entity instanceof OceannizedWitheriaEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceannizedWitheriaEntity.DATA_skillp, (int) (skillp - 1));
		} else {
			if (!(enemy == null) && enemy.isAlive()) {
				if (entity instanceof OceannizedWitheriaEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceannizedWitheriaEntity.DATA_duration, 65);
				if (entity instanceof OceannizedWitheriaEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceannizedWitheriaEntity.DATA_skillp, 400);
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 65, 0, false, false));
				if (entity instanceof OceannizedWitheriaEntity) {
					((OceannizedWitheriaEntity) entity).setAnimation("animation.oceanzied_witheria.skill");
				}
				CaerulaArborMod.queueServerWork(20, () -> {
					if (entity.isAlive()) {
						RoundWitheriaShootProcedure.execute(world, entity);
					}
				});
				CaerulaArborMod.queueServerWork(27, () -> {
					if (entity.isAlive()) {
						PurchaseEnemyProcedure.execute(entity);
						WitheriaSweepProcedure.execute(world, entity, -2, 0.75);
					}
				});
				CaerulaArborMod.queueServerWork(30, () -> {
					if (entity.isAlive()) {
						PurchaseEnemyProcedure.execute(entity);
						AdditionWithriaShootProcedure.execute(world, x, y, z, entity);
						WitheriaSweepProcedure.execute(world, entity, -2, 0.75);
					}
				});
				CaerulaArborMod.queueServerWork(48, () -> {
					if (entity.isAlive()) {
						PurchaseEnemyProcedure.execute(entity);
						WitheriaSweepProcedure.execute(world, entity, 0, 1.5);
					}
				});
			}
		}
		if (entity.tickCount % 10 == 0) {
			WitheriaDestoryBlockProcedure.execute(world, x, y, z);
		}
		if (entity.tickCount % 20 == 0) {
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.WITHER);
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(CaerulaArborModMobEffects.DIZZY.get());
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(64 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof LivingEntity _livEnt31 && _livEnt31.hasEffect(MobEffects.WITHER) && entityiterator.isAlive()) {
						entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_wither"))), entity),
								(float) ((entity instanceof LivingEntity _livingEntity33 && _livingEntity33.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity33.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 0.75));
						if (world instanceof ServerLevel _level)
							_level.sendParticles(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, (entityiterator.getX()), (entityiterator.getY() + 1), (entityiterator.getZ()), 16, 1, 1, 1, 0.1);
					}
				}
			}
		}
		if (entity.tickCount % 40 == 0) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(72 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof WitherSkull && GetSpeedProcedure.execute(entityiterator) < 0.15) {
						if (!entityiterator.level().isClientSide())
							entityiterator.discard();
					}
				}
			}
		}
		if (GetSpeedProcedure.execute(entity) > (entity instanceof LivingEntity _livingEntity45 && _livingEntity45.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED) ? _livingEntity45.getAttribute(Attributes.MOVEMENT_SPEED).getValue() : 0)) {
			entity.setDeltaMovement(new Vec3(0, 0, 0));
		}
		shelled = entity instanceof OceannizedWitheriaEntity _datEntL47 && _datEntL47.getEntityData().get(OceannizedWitheriaEntity.DATA_shelled);
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5 && !shelled) {
			if (entity instanceof LivingEntity _livingEntity51 && _livingEntity51.getAttributes().hasAttribute(Attributes.ARMOR))
				_livingEntity51.getAttribute(Attributes.ARMOR)
						.setBaseValue(((entity instanceof LivingEntity _livingEntity50 && _livingEntity50.getAttributes().hasAttribute(Attributes.ARMOR) ? _livingEntity50.getAttribute(Attributes.ARMOR).getBaseValue() : 0) * 1.5));
			if (entity instanceof LivingEntity _livingEntity53 && _livingEntity53.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
				_livingEntity53.getAttribute(Attributes.ATTACK_DAMAGE)
						.setBaseValue(((entity instanceof LivingEntity _livingEntity52 && _livingEntity52.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity52.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0) * 1.5));
			if (entity instanceof LivingEntity _livingEntity55 && _livingEntity55.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
				_livingEntity55.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
						.setBaseValue(((entity instanceof LivingEntity _livingEntity54 && _livingEntity54.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
								? _livingEntity54.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).getBaseValue()
								: 0) * 1.5));
			if (entity instanceof OceannizedWitheriaEntity animatable)
				animatable.setTexture("oceanized_witheria_anger");
			if (entity instanceof OceannizedWitheriaEntity _datEntSetL)
				_datEntSetL.getEntityData().set(OceannizedWitheriaEntity.DATA_shelled, true);
		}
		if (idle > 1800) {
			if (entity instanceof OceannizedWitheriaEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceannizedWitheriaEntity.DATA_skillp, 1800);
			if (entity instanceof OceannizedWitheriaEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceannizedWitheriaEntity.DATA_duration, 1800);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 999, 9, false, false));
			if (entity instanceof OceannizedWitheriaEntity) {
				((OceannizedWitheriaEntity) entity).setAnimation("animation.oceanzied_witheria.byebye");
			}
			CaerulaArborMod.queueServerWork(100, () -> {
				if (!entity.level().isClientSide())
					entity.discard();
			});
		}
	}
}
