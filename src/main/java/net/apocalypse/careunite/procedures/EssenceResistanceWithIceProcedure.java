package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class EssenceResistanceWithIceProcedure {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			execute(event, event.player);
		}
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (entity.tickCount % 2 == 0 && entity instanceof LivingEntity _livEnt1 && _livEnt1.hasEffect(CaerulaArborModMobEffects.ESSENCE_RESISTANCE.get())) {
			if(entity.getTicksFrozen() < 140) entity.setTicksFrozen((int) Math.max(entity.getTicksFrozen() - 1, 0));
			entity.setRemainingFireTicks((int) Math.max(entity.getRemainingFireTicks() - 1, 0));
		}
	}
}
