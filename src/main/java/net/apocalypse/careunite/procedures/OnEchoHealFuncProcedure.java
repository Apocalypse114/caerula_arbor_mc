package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;

@Mod.EventBusSubscriber
public class OnEchoHealFuncProcedure {
	@SubscribeEvent
	public static void onEntityHealed(LivingHealEvent event) {
		LivingEntity entity = event.getEntity();
		Level world = entity.level();
		float amount = event.getAmount();
		float newAmount = amount;
		if (entity == null)
			return;
		double migEvo = 0;
		if (entity instanceof Player && GetEchoFightProcedure.execute(entity) != 0) {
			migEvo = CaerulaArborModVariables.MapVariables.get(world).strategy_migration;
			if (migEvo >= 3) {
				newAmount *= 0.15;
			} else {
				newAmount *= 0.3;
			}
		}
		if (entity.hasEffect(CaerulaArborModMobEffects.SEABORN_DECREASE.get())) {
			newAmount *= 0.5;
		} 
		if (newAmount < amount){
			event.setAmount(newAmount);
		}
	}

	public static void execute(LevelAccessor world, Entity entity, double amount) {
	}
}
