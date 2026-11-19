package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.TideChimeraEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class ChimeraReefbreakerProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getSource().getEntity());
		}
	}

	public static void execute(Entity sourceentity) {
		execute(null, sourceentity);
	}

	private static void execute(@Nullable Event event, Entity sourceentity) {
		if (sourceentity == null)
			return;
		double amplifi = 0;
		if (sourceentity instanceof TideChimeraEntity) {
			amplifi = sourceentity instanceof LivingEntity _livEnt && _livEnt.hasEffect(CaerulaArborModMobEffects.REEF_CRACKER.get()) ? _livEnt.getEffect(CaerulaArborModMobEffects.REEF_CRACKER.get()).getAmplifier() : 0;
			if (sourceentity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(CaerulaArborModMobEffects.REEF_CRACKER.get())) {
				if (amplifi < 31) {
					if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.REEF_CRACKER.get(), 100, (int) (amplifi + 1), false, false));
				} else {
					if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.REEF_CRACKER.get(), 100, 31, false, false));
				}
			} else {
				if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.REEF_CRACKER.get(), 100, 0, false, false));
			}
		}
	}
}
