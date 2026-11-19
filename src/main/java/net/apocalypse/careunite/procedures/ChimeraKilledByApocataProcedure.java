package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.TideChimeraEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class ChimeraKilledByApocataProcedure {
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
		String name = "";
		if (entity instanceof TideChimeraEntity) {
			name = sourceentity.getDisplayName().getString();
			if (name.contains("apocata") || name.contains("Apocata")) {
				if (event instanceof LivingHurtEvent _hurt) {
					_hurt.setAmount((float) Math.max(1000000, amount));
				}
			}
		}
	}
}
