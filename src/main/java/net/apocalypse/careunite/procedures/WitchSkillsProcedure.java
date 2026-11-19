package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.OceanziedWitchEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class WitchSkillsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double sklp = 0;
		Entity enemy = null;
		if (entity.isAlive()) {
			sklp = entity instanceof OceanziedWitchEntity _datEntI ? _datEntI.getEntityData().get(OceanziedWitchEntity.DATA_skillp) : 0;
			if (sklp <= 0) {
				enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
				if (!(enemy == null) && enemy.isAlive() && (enemy != null ? entity.distanceTo(enemy) : -1) <= 9) {
					if (entity instanceof OceanziedWitchEntity) {
						((OceanziedWitchEntity) entity).setAnimation("animation.oceanized_witch.throw");
					}
					if (entity instanceof OceanziedWitchEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanziedWitchEntity.DATA_skillp, 250);
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 60, 0, false, false));
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 9, false, false));
					CaerulaArborMod.queueServerWork(14, () -> {
						ShootRandomPotionProcedure.execute(entity);
					});
					CaerulaArborMod.queueServerWork(19, () -> {
						ShootRandomPotionProcedure.execute(entity);
					});
					CaerulaArborMod.queueServerWork(23, () -> {
						ShootRandomPotionProcedure.execute(entity);
					});
					CaerulaArborMod.queueServerWork(28, () -> {
						ShootRandomPotionProcedure.execute(entity);
					});
					CaerulaArborMod.queueServerWork(29, () -> {
						ShootRandomPotionProcedure.execute(entity);
					});
					CaerulaArborMod.queueServerWork(34, () -> {
						ShootRandomPotionProcedure.execute(entity);
					});
					CaerulaArborMod.queueServerWork(36, () -> {
						ShootRandomPotionProcedure.execute(entity);
					});
					CaerulaArborMod.queueServerWork(41, () -> {
						ShootRandomPotionProcedure.execute(entity);
					});
				}
			} else {
				if (entity instanceof OceanziedWitchEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanziedWitchEntity.DATA_skillp, (int) (sklp - 1));
			}
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.POISON);
			if ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? _livEnt.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() : 0) < 4) {
				if (entity instanceof LivingEntity _entity)
					_entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
			}
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.WEAKNESS);
			RaiderRideRavagerProcedure.execute(world, x, y, z, entity);
		}
	}
}
