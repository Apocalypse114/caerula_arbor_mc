package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.ChiselerFishEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class LockFuncProcedure {
	@SubscribeEvent
	public static void onEntitySetsAttackTarget(LivingChangeTargetEvent event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity sourceentity) {
		execute(null, sourceentity);
	}

	private static void execute(@Nullable Event event, Entity sourceentity) {
		if (sourceentity == null)
			return;
		if (sourceentity instanceof ChiselerFishEntity && !(sourceentity instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(CaerulaArborModMobEffects.COOLDOWN_SINAL.get()))) {
			if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 3, false, false));
			if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 1));
			if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COOLDOWN_SINAL.get(), 800, 0, false, false));
		}
	}
}
