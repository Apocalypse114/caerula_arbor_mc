package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.TidutantExcrescenceEntity;
import net.apocalypse.careunite.entity.TideutantRockSpiderEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class TidutantArmorBreakProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(Entity entity, Entity sourceentity) {
		execute(null, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (sourceentity instanceof TideutantRockSpiderEntity) {
			GiveLessArmorProcedure.execute(entity, 8);
		} else if (sourceentity instanceof TidutantExcrescenceEntity) {
			GiveLessArmorProcedure.execute(entity, 4);
		}
	}
}
