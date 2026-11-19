package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class OnMobEchoTickProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double migEvo = 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (enemy instanceof Player) {
			if (GetEchoExploreProcedure.execute(enemy) != 0) {
				migEvo = CaerulaArborModVariables.MapVariables.get(world).strategy_migration;
				if (migEvo >= 3) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_KNOCKACK_RESISTANVCE.get(), 40, 1, false, false));
				} else {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_KNOCKACK_RESISTANVCE.get(), 40, 0, false, false));
				}
			}
			if (GetEchoCrowdProcedure.execute(enemy) != 0) {
				migEvo = CaerulaArborModVariables.MapVariables.get(world).evo_point_breed;
				if (migEvo >= 3) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ECHO_CROWD.get(), 40, 2, false, false));
				} else {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ECHO_CROWD.get(), 40, 1, false, false));
				}
			}
		}
	}
}
