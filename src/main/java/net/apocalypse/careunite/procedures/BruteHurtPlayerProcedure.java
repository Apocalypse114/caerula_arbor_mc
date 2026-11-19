package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedBruteEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class BruteHurtPlayerProcedure {
	@SubscribeEvent
	public static void entityTakesDamage(LivingDamageEvent event) {
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
		String str = "";
		String name = "";
		if (sourceentity instanceof OceanizedBruteEntity && entity instanceof Player) {
			str = sourceentity.getPersistentData().getString("hurtPlayer");
			name = entity.getDisplayName().getString();
			if (!str.contains(name)) {
				sourceentity.getPersistentData().putString("hurtPlayer", (str + "," + name));
			}
		}
	}
}
