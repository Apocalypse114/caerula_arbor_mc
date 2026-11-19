package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class EndspeakerttackFuncProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, Entity sourceentity) {
		execute(null, world, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity sourceentity) {
		if (sourceentity == null)
			return;
		double amplifi = 0;
		if (RecogEndspeakerProcedure.execute(sourceentity)) {
			if (InquirybilityProcedure.execute(world, 5)) {
				amplifi = sourceentity instanceof LivingEntity _livEnt && _livEnt.hasEffect(CaerulaArborModMobEffects.REEF_CRACKER.get()) ? _livEnt.getEffect(CaerulaArborModMobEffects.REEF_CRACKER.get()).getAmplifier() : 0;
				if (sourceentity instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(CaerulaArborModMobEffects.REEF_CRACKER.get())) {
					if (amplifi < 11) {
						if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.REEF_CRACKER.get(), 120, (int) (amplifi + 1), false, false));
					} else {
						if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.REEF_CRACKER.get(), 80, 11, false, false));
					}
				} else {
					if (sourceentity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.REEF_CRACKER.get(), 80, 0, false, false));
				}
			}
		}
	}
}
