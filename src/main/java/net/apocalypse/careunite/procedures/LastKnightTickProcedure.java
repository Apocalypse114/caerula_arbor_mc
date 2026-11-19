package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.TheLastKnightEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class LastKnightTickProcedure {
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
		skillp = entity instanceof TheLastKnightEntity _datEntI ? _datEntI.getEntityData().get(TheLastKnightEntity.DATA_skillp) : 0;
		duration = entity instanceof TheLastKnightEntity _datEntI ? _datEntI.getEntityData().get(TheLastKnightEntity.DATA_duration) : 0;
		if (duration > 0) {
			if (entity instanceof TheLastKnightEntity _datEntSetI)
				_datEntSetI.getEntityData().set(TheLastKnightEntity.DATA_duration, (int) (duration - 1));
		}
		entity.setTicksFrozen(0);
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(CaerulaArborModMobEffects.FROZEN.get());
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (skillp > 0) {
			if (entity instanceof TheLastKnightEntity _datEntSetI)
				_datEntSetI.getEntityData().set(TheLastKnightEntity.DATA_skillp, (int) (skillp - 1));
		} else {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) < 4) {
					if (entity instanceof TheLastKnightEntity _datEntSetI)
						_datEntSetI.getEntityData().set(TheLastKnightEntity.DATA_duration, 90);
					if (entity instanceof TheLastKnightEntity _datEntSetI)
						_datEntSetI.getEntityData().set(TheLastKnightEntity.DATA_skillp, 390);
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 30, 0, false, false));
					if (entity instanceof TheLastKnightEntity) {
						((TheLastKnightEntity) entity).setAnimation("animation.last_knight.skill");
					}
					CaerulaArborMod.queueServerWork(27, () -> {
						if (entity.isAlive()) {
							KnightCrossAttackProcedure.execute(world, x, y, z, entity);
						}
					});
					CaerulaArborMod.queueServerWork(45, () -> {
						if (entity.isAlive()) {
							KnightCrossAttackProcedure.execute(world, x, y, z, entity);
						}
					});
					CaerulaArborMod.queueServerWork(57, () -> {
						if (entity.isAlive()) {
							KnightCrossAttackProcedure.execute(world, x, y, z, entity);
						}
					});
				}
			}
		}
	}
}
