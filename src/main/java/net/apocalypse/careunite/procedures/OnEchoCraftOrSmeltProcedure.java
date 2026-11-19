package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class OnEchoCraftOrSmeltProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double migEvo = 0;
		double perc = 0;
		ItemStack reward = ItemStack.EMPTY;
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(CaerulaArborModMobEffects.COOLDOWN_SINAL.get())) {
			return;
		}
		if (GetEchoRemouldProcedure.execute(entity) != 0) {
			migEvo = CaerulaArborModVariables.MapVariables.get(world).evo_point_subsisting;
			if (migEvo >= 3) {
				perc = 0.035;
			} else {
				perc = 0.025;
			}
			if (Math.random() < perc) {
				GiveEchoRewardProcedure.execute(world, x, y, z, entity);
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COOLDOWN_SINAL.get(), 40, 0, false, false));
			}
		}
		if (GetEchoAdaptProcedure.execute(entity) != 0) {
			migEvo = CaerulaArborModVariables.MapVariables.get(world).evo_point_grow;
			if (migEvo >= 3) {
				perc = 0.015;
			} else {
				perc = 0.01;
			}
			if (Math.random() < perc) {
				RewardEchoRelicProcedure.execute(world, x, y, z, entity);
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COOLDOWN_SINAL.get(), 40, 0, false, false));
			}
		}
	}
}
