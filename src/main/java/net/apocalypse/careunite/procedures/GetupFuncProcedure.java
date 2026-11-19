package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;

import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

import javax.annotation.Nullable;
import net.minecraft.world.level.Level;

@Mod.EventBusSubscriber
public class GetupFuncProcedure {
	
	@SubscribeEvent
	public static void onEntityEndSleep(PlayerWakeUpEvent event) {
		if (!event.updateLevel() && !event.wakeImmediately())
			execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		Level level = entity.level();
		RevivesPlayerLightsProcedure.execute(entity, Mth.nextInt(level.getRandom(), 1, 3));
		if (Mth.nextDouble(level.getRandom(), 0, 1) < 0.2) {
			ChangePlayerLPOrShieldProcedure.execute(level, entity, false, 1, "sleep");
		}
	}
}
