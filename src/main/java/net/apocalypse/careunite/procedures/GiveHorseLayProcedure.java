package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class GiveHorseLayProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		Entity rider = null;
		if (ModGriefSettingsProcedure.execute(world) && (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5) {
			if (!(entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(CaerulaArborModMobEffects.MUTE.get()))) {
				if (!(entity instanceof LivingEntity _livEnt3 && _livEnt3.hasEffect(CaerulaArborModMobEffects.GUIDE_PATH_AHEAD.get()))) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.GUIDE_PATH_AHEAD.get(), 20, 0));
				}
			}
		}
		if ((entity.tickCount - (entity instanceof LivingEntity _livEnt ? _livEnt.getLastHurtMobTimestamp() : 0)) % 10 == 0) {
			rider = entity.getFirstPassenger();
			if (!(rider == null) && rider.isAlive()) {
				if (rider instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_ATTACK_PERCLY.get(), 40, 1, false, true));
			}
		}
	}
}
