package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class PlayerEvoHitFuncProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getSource(), event.getEntity(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(DamageSource damagesource, Entity entity, Entity sourceentity, double amount) {
		execute(null, damagesource, entity, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, DamageSource damagesource, Entity entity, Entity sourceentity, double amount) {
		if (damagesource == null || entity == null || sourceentity == null)
			return;
		boolean isIndirect = false;
		double rate = 0;
		double e = 0;
		double finalValue = 0;
		double lll = 0;
		if (sourceentity instanceof Player && CanPlayerEvoProcedure.execute(sourceentity)) {
			rate = 1;
			e = GetNodeAddDamageProcedure.execute(sourceentity);
			isIndirect = damagesource.isIndirect();
			if (e >= 1 && !isIndirect) {
				rate = 1.2;
			}
			if (e >= 2 && isIndirect) {
				rate = 1.2;
			}
			if (e >= 3 && !isIndirect) {
				rate = 1.6;
			}
			if (e >= 4 && isIndirect) {
				rate = 1.6;
			}
			if (rate > 1) {
				finalValue = amount * rate;
			}
			if (entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(CaerulaArborModMobEffects.UNDER_BREAK.get())) {
				e = GetNodeWorseBreakProcedure.execute(sourceentity);
				if (e >= 4) {
					rate = 2.4;
				} else if (e >= 3) {
					rate = 1.9;
				} else if (e >= 2) {
					rate = 1.5;
				} else if (e >= 1) {
					rate = 1.2;
				}
				if (rate > 1) {
					finalValue = finalValue * rate;
				}
			}
			e = GetNodeLessArmorProcedure.execute(sourceentity);
			if (e > 0) {
				if (e >= 4) {
					rate = 1.4;
					lll = 23;
				} else if (e >= 3) {
					rate = 1.15;
					lll = 23;
				} else if (e >= 2) {
					rate = 1;
					lll = 17;
				} else if (e >= 1) {
					rate = 1;
					lll = 17;
				}
				for (int index0 = 0; index0 < (int) e; index0++) {
					GiveLessArmorProcedure.execute(entity, lll);
				}
				if ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(CaerulaArborModMobEffects.LESS_ARMOR.get()) ? _livEnt.getEffect(CaerulaArborModMobEffects.LESS_ARMOR.get()).getAmplifier() : 0) >= lll) {
					if (rate > 1) {
						finalValue = finalValue * rate;
					}
				}
			}
			if (IsNexusPercDamageSelectedProcedure.execute(sourceentity)) {
				finalValue = Math.min(Math.max(GetPlayerDamagePercProcedure.execute(entity, sourceentity), finalValue), finalValue * 32);
			}
			if (finalValue > amount) {
				if (event instanceof LivingHurtEvent _hurt) {
					_hurt.setAmount((float) finalValue);
				}
			}
		}
	}
}
