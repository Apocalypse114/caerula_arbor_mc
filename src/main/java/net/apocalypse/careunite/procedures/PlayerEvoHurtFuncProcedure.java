package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class PlayerEvoHurtFuncProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getSource(), event.getEntity(), event.getAmount());
		}
	}

	public static void execute(DamageSource damagesource, Entity entity, double amount) {
		execute(null, damagesource, entity, amount);
	}

	private static void execute(@Nullable Event event, DamageSource damagesource, Entity entity, double amount) {
		if (damagesource == null || entity == null)
			return;
		boolean isIndirect = false;
		double rate = 0;
		double e = 0;
		double finalAmount = 0;
		if (entity instanceof Player && CanPlayerEvoProcedure.execute(entity)) {
			rate = 1;
			e = GetNodeLessDamageProcedure.execute(entity);
			finalAmount = amount;
			isIndirect = damagesource.isIndirect();
			if (IsNexusExpoShieldSelectedProcedure.execute(entity)) {
				finalAmount = Math.min(32 * Math.pow(finalAmount / 32, 0.75), finalAmount);
			}
			if (e >= 1 && !isIndirect) {
				rate = 0.85;
			}
			if (e >= 2 && isIndirect) {
				rate = 0.85;
			}
			if (e >= 3 && !isIndirect) {
				rate = 0.5;
			}
			if (e >= 4 && isIndirect) {
				rate = 0.5;
			}
			if (rate < 1) {
				finalAmount = finalAmount * rate;
			}
			if (GetHealthPercProcedure.execute(entity) <= 0.5) {
				e = GetNodeEutectesProcedure.execute(entity);
				if (e >= 4) {
					rate = 0.5;
				} else if (e >= 3) {
					rate = 0.3;
				} else if (e >= 2) {
					rate = 0.15;
				} else if (e >= 1) {
					rate = 0.05;
				}
				if (rate < 1) {
					finalAmount = finalAmount * rate;
				}
			}
			if (finalAmount < amount) {
				if (event instanceof LivingHurtEvent _hurt) {
					_hurt.setAmount((float) finalAmount);
				}
			}
		}
	}
}
