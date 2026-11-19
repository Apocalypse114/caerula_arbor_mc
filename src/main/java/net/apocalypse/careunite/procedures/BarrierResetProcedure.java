package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

import javax.annotation.Nullable;
import net.minecraftforge.eventbus.api.EventPriority;

@Mod.EventBusSubscriber
public class BarrierResetProcedure {
	@SubscribeEvent
(priority = EventPriority.LOWEST)
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity());
		}
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (!event.isCanceled()) {
			if (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.LIVING_BARRIER.get()))
				_livingEntity1.getAttribute(CaerulaArborModAttributes.LIVING_BARRIER.get()).setBaseValue(0);
			entity.getPersistentData().putDouble("playerEvoHitTime", 0);
		}
	}
}
