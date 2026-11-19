package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class EndspeakerHurtFuncProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity, double amount) {
		execute(null, world, entity, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, Entity sourceentity, double amount) {
		if (entity == null || sourceentity == null)
			return;
		if (InquirybilityProcedure.execute(world, 3)) {
			if (RecogEndspeakerProcedure.execute(sourceentity) && entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(CaerulaArborModMobEffects.TRAIL_BUFF.get())) {
				if (event instanceof LivingHurtEvent _hurt) {
					_hurt.setAmount((float) (amount * 1.5));
				}
			}
			if (RecogEndspeakerProcedure.execute(entity)
					&& (entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(CaerulaArborModMobEffects.TRAIL_BUFF.get()) || sourceentity instanceof LivingEntity _livEnt3 && _livEnt3.hasEffect(CaerulaArborModMobEffects.TRAIL_BUFF.get()))) {
				if (event instanceof LivingHurtEvent _hurt) {
					_hurt.setAmount((float) (amount * 0.65));
				}
			}
		}
	}
}
