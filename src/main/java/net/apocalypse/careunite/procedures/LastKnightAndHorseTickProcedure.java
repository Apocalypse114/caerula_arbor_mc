package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.LastKnightAndHorseEntity;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class LastKnightAndHorseTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double add = 0;
		double duration = 0;
		double skillp = 0;
		if (!entity.isAlive()) {
			return;
		}
		if (entity.tickCount % 10 == 0) {
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(CaerulaArborModMobEffects.DIZZY.get());
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(CaerulaArborModMobEffects.FROZEN.get());
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 20, 0, false, false));
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) > (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5) {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(48 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator.getTicksFrozen() >= 125 && entityiterator.isAlive()) {
							entityiterator.setTicksFrozen(200);
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.FROZEN.get(), 20, 0, false, false));
						}
					}
				}
			}
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (!(enemy == null) && enemy.isAlive()) {
				add = entity instanceof LastKnightAndHorseEntity _datEntI ? _datEntI.getEntityData().get(LastKnightAndHorseEntity.DATA_addiiton) : 0;
				if (add < 20) {
					if (entity instanceof LastKnightAndHorseEntity _datEntSetI)
						_datEntSetI.getEntityData().set(LastKnightAndHorseEntity.DATA_addiiton, (int) (add + 1));
				}
			}
		}
		if (GetSpeedProcedure.execute(entity) > (entity instanceof LivingEntity _livingEntity18 && _livingEntity18.getAttributes().hasAttribute(Attributes.MOVEMENT_SPEED) ? _livingEntity18.getAttribute(Attributes.MOVEMENT_SPEED).getValue() : 0)
				* 1.25) {
			entity.setDeltaMovement(new Vec3(0, 0, 0));
		}
		entity.setTicksFrozen(0);
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(CaerulaArborModMobEffects.FROZEN.get());
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(CaerulaArborModMobEffects.DIZZY.get());
		skillp = entity instanceof LastKnightAndHorseEntity _datEntI ? _datEntI.getEntityData().get(LastKnightAndHorseEntity.DATA_skillp) : 0;
		duration = entity instanceof LastKnightAndHorseEntity _datEntI ? _datEntI.getEntityData().get(LastKnightAndHorseEntity.DATA_duration) : 0;
		if (duration > 0) {
			if (entity instanceof LastKnightAndHorseEntity _datEntSetI)
				_datEntSetI.getEntityData().set(LastKnightAndHorseEntity.DATA_duration, (int) (duration - 1));
		}
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (skillp > 0) {
			if (entity instanceof LastKnightAndHorseEntity _datEntSetI)
				_datEntSetI.getEntityData().set(LastKnightAndHorseEntity.DATA_skillp, (int) (skillp - 1));
		} else {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) < 4) {
					if (entity instanceof LastKnightAndHorseEntity _datEntSetI)
						_datEntSetI.getEntityData().set(LastKnightAndHorseEntity.DATA_duration, 40);
					if (entity instanceof LastKnightAndHorseEntity _datEntSetI)
						_datEntSetI.getEntityData().set(LastKnightAndHorseEntity.DATA_skillp, 240);
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 32, 0, false, false));
					if (entity instanceof LastKnightAndHorseEntity) {
						((LastKnightAndHorseEntity) entity).setAnimation("animation.last_knight_horse.skill");
					}
					CaerulaArborMod.queueServerWork(13, () -> {
						KnightHorseFlyProcedure.execute(world, x, y, z, entity);
					});
				}
			}
		}
	}
}
