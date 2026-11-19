package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.entity.TheLastKnightEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class KnightAddedAttackProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity(), event.getSource().getEntity(), event.getAmount());
		}
	}

	public static void execute(Entity entity, Entity sourceentity, double amount) {
		execute(null, entity, sourceentity, amount);
	}

	private static void execute(@Nullable Event event, Entity entity, Entity sourceentity, double amount) {
		if (entity == null || sourceentity == null)
			return;
		double rate = 0;
		if (sourceentity instanceof TheLastKnightEntity) {
			rate = 1;
			if (entity.getTicksFrozen() >= 200) {
				rate = 1.75;
			}
			if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
				rate = rate * 1.5;
			}
			if (event instanceof LivingHurtEvent _hurt) {
				_hurt.setAmount((float) (amount * rate));
			}
		}
	}
}
