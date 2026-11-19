package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.InteractionHand;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class DebugProcedure {
	@SubscribeEvent
	public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
		String usehand = "";
		if (event.getHand() == InteractionHand.MAIN_HAND) {
			usehand = "mainhand";
		} else {
			usehand = "offhand";
		}
		execute(event);
	}

	public static void execute() {
		execute(null);
	}

	private static void execute(@Nullable Event event) {
	}
}
