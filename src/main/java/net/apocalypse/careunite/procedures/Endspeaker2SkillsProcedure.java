package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.Endspeaker2Entity;
import net.apocalypse.careunite.CaerulaArborMod;

public class Endspeaker2SkillsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		boolean iEvol = false;
		double gap = 0;
		double sklp1 = 0;
		double dura = 0;
		double evo = 0;
		if (!entity.isAlive()) {
			return;
		}
		if (entity.tickCount < 35) {
			return;
		}
		evo = entity instanceof Endspeaker2Entity _datEntI ? _datEntI.getEntityData().get(Endspeaker2Entity.DATA_EvolveTime) : 0;
		if (evo > 0) {
			if (entity instanceof Endspeaker2Entity _datEntSetL)
				_datEntSetL.getEntityData().set(Endspeaker2Entity.DATA_isEvolving, true);
			if (entity instanceof LivingEntity _entity)
				_entity.setHealth((float) Math.max(Math.round((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * (300 - evo) * 0.0033333), 1));
			if (!(entity instanceof LivingEntity _livEnt6 && _livEnt6.hasEffect(CaerulaArborModMobEffects.INVULNERABLE.get()))) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 300, 1, false, false));
			}
			if (evo <= 260) {
				if (!(entity instanceof LivingEntity _livEnt8 && _livEnt8.hasEffect(MobEffects.INVISIBILITY))) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 300, 0, false, false));
				}
			}
			if (evo == 1) {
				EndspeakerToPhase2Procedure.execute(world, x, y, z, entity, 3);
				if (!entity.level().isClientSide())
					entity.discard();
			}
			if (entity instanceof Endspeaker2Entity _datEntSetI)
				_datEntSetI.getEntityData().set(Endspeaker2Entity.DATA_EvolveTime, (int) (evo - 1));
		} else {
			if (entity instanceof Endspeaker2Entity _datEntSetL)
				_datEntSetL.getEntityData().set(Endspeaker2Entity.DATA_isEvolving, false);
		}
		iEvol = entity instanceof Endspeaker2Entity _datEntL13 && _datEntL13.getEntityData().get(Endspeaker2Entity.DATA_isEvolving);
		gap = 400;
		if (iEvol) {
			gap = 80;
		}
		if (entity.tickCount % gap == 33 && GetSeabornNumProcedure.execute(world, x, y, z) < 32) {
			EndspeakerSpawnMobsProcedure.execute(world, x, y, z, entity, 0.1, 5);
		}
		EndspeakerTickFuncProcedure.execute(world, entity);
		sklp1 = entity instanceof Endspeaker2Entity _datEntI ? _datEntI.getEntityData().get(Endspeaker2Entity.DATA_skillp) : 0;
		dura = entity instanceof Endspeaker2Entity _datEntI ? _datEntI.getEntityData().get(Endspeaker2Entity.DATA_duration) : 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (dura > 0) {
			if (entity instanceof Endspeaker2Entity _datEntSetI)
				_datEntSetI.getEntityData().set(Endspeaker2Entity.DATA_duration, (int) (dura - 1));
		}
		if (iEvol) {
			if (entity instanceof Endspeaker2Entity _datEntSetI)
				_datEntSetI.getEntityData().set(Endspeaker2Entity.DATA_skillp, 10000);
			return;
		}
		if (sklp1 > 0) {
			if (entity instanceof Endspeaker2Entity _datEntSetI)
				_datEntSetI.getEntityData().set(Endspeaker2Entity.DATA_skillp, (int) (sklp1 - 1));
		} else {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 4) {
					if (entity instanceof Endspeaker2Entity) {
						((Endspeaker2Entity) entity).setAnimation("animation.endspeaker_2.skill");
					}
					if (entity instanceof Endspeaker2Entity _datEntSetI)
						_datEntSetI.getEntityData().set(Endspeaker2Entity.DATA_duration, 36);
					if (entity instanceof Endspeaker2Entity _datEntSetI)
						_datEntSetI.getEntityData().set(Endspeaker2Entity.DATA_skillp, 170);
					CaerulaArborMod.queueServerWork(9, () -> {
						if (entity.isAlive()) {
							EndspeakerRangedFlyProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity);
						}
					});
					CaerulaArborMod.queueServerWork(19, () -> {
						if (entity.isAlive()) {
							EndspeakerrangedSitProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity);
						}
					});
				}
			}
		}
	}
}
