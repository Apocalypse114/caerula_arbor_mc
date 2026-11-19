package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.Endspeaker0Entity;

public class Endspeaker0SkillsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double gap = 0;
		double evo = 0;
		if (!entity.isAlive()) {
			return;
		}
		evo = entity instanceof Endspeaker0Entity _datEntI ? _datEntI.getEntityData().get(Endspeaker0Entity.DATA_EvolveTime) : 0;
		if (evo > 0) {
			if (entity instanceof Endspeaker0Entity _datEntSetL)
				_datEntSetL.getEntityData().set(Endspeaker0Entity.DATA_isEvolving, true);
			if (entity instanceof LivingEntity _entity)
				_entity.setHealth((float) Math.max(Math.round((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * (300 - evo) * 0.0033333), 1));
			if (!(entity instanceof LivingEntity _livEnt5 && _livEnt5.hasEffect(CaerulaArborModMobEffects.INVULNERABLE.get()))) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 300, 1, false, false));
			}
			if (evo <= 260) {
				if (!(entity instanceof LivingEntity _livEnt7 && _livEnt7.hasEffect(MobEffects.INVISIBILITY))) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 300, 0, false, false));
				}
			}
			if (evo == 1) {
				EndspeakerToPhase2Procedure.execute(world, x, y, z, entity, 1);
				if (!entity.level().isClientSide())
					entity.discard();
			}
			if (entity instanceof Endspeaker0Entity _datEntSetI)
				_datEntSetI.getEntityData().set(Endspeaker0Entity.DATA_EvolveTime, (int) (evo - 1));
		} else {
			if (entity instanceof Endspeaker0Entity _datEntSetL)
				_datEntSetL.getEntityData().set(Endspeaker0Entity.DATA_isEvolving, false);
		}
		gap = 600;
		if (entity instanceof Endspeaker0Entity _datEntL12 && _datEntL12.getEntityData().get(Endspeaker0Entity.DATA_isEvolving)) {
			gap = 100;
		}
		if (entity.tickCount % gap == 33 && GetSeabornNumProcedure.execute(world, x, y, z) < 32) {
			EndspeakerSpawnMobsProcedure.execute(world, x, y, z, entity, 0.05, 3);
		}
	}
}
