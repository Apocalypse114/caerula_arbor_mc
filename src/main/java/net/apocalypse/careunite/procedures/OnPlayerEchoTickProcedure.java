package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class OnPlayerEchoTickProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double migEvo = 0;
		if (GetEchoExploreProcedure.execute(entity) != 0) {
			migEvo = CaerulaArborModVariables.MapVariables.get(world).strategy_migration;
			if (migEvo >= 3) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_KNOCKACK_RESISTANVCE.get(), 40, 1, false, false));
			} else {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_KNOCKACK_RESISTANVCE.get(), 40, 0, false, false));
			}
		}
		if (GetEchoFightProcedure.execute(entity) != 0) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ECHO_FIGHT.get(), 40, 0, false, false));
			SyncEchoFightBuffProcedure.execute(world, entity);
		}
		if (entity.tickCount % 600 == 0) {
			if (GetEchoAdaptProcedure.execute(entity) != 0) {
				migEvo = CaerulaArborModVariables.MapVariables.get(world).strategy_grow;
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200, 0, false, false));
				if (migEvo >= 3) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.SUB_HAEMO.get(), 160, 0, false, false));
				}
			}
		}
	}
}
