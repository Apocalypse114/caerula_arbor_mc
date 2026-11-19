package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class OnEchoPlayerHurtProcedure {
	public static void execute(LevelAccessor world, DamageSource damagesource, Entity entity, double amount) {
		if (damagesource == null || entity == null)
			return;
		double evo = 0;
		double perc = 0;
		if (IsSeabornProcedure.execute(damagesource.getEntity()) && GetEchoRemouldProcedure.execute(entity) != 0) {
			evo = CaerulaArborModVariables.MapVariables.get(world).evo_point_subsisting;
			if (evo >= 3) {
				perc = 0.075;
			} else {
				perc = 0.05;
			}
			if (Math.random() < perc) {
				PlayerDropSmartProcedure.execute(entity);
				if (perc > 0.06) {
					PlayerDropSmartProcedure.execute(entity);
				}
			}
		}
		if (entity instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(CaerulaArborModMobEffects.COOLDOWN_SINAL.get())) {
			return;
		}
		if (GetEchoFeedProcedure.execute(entity) != 0) {
			if (Math.random() < 0.1) {
				evo = CaerulaArborModVariables.MapVariables.get(world).evo_point_breed;
				if (evo >= 3) {
					LivingHeal1Procedure.execute(entity, amount * 0.3);
				} else {
					LivingHeal1Procedure.execute(entity, amount * 0.25);
				}
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COOLDOWN_SINAL.get(), 20, 0, false, false));
			}
			if (evo >= 3) {
				if ((damagesource.getEntity()) instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ECHO_FEED.get(), 200, 1, false, false));
			} else {
				if ((damagesource.getEntity()) instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ECHO_FEED.get(), 200, 0, false, false));
			}
		}
	}
}
