package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

import javax.annotation.Nullable;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class ServerTickFuncProcedure {
	@SubscribeEvent
	public static void onWorldTick(TickEvent.LevelTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			execute(event, event.level);
		}
	}

	public static void execute(LevelAccessor world) {
		execute(null, world);
	}

	private static void execute(@Nullable Event event, LevelAccessor world) {
		if (world.dayTime() > 3333 && world.dayTime() % 24000 == 3333) {
			CaerulaArborModVariables.MapVariables.get(world).traderSpawnGapCheck = true;
			CaerulaArborModVariables.MapVariables.get(world).syncData(world);
			for (Entity entityiterator : new ArrayList<>(world.players())) {
				if (ShouldSpawnTraderProcedure.execute(world, entityiterator)) {
					DoSpawnTraderProcedure.execute(world, entityiterator.getX(), entityiterator.getZ());
					CaerulaArborModVariables.MapVariables.get(world).traderSpawnGapCheck = false;
					CaerulaArborModVariables.MapVariables.get(world).syncData(world);
					break;
				} else {
					CaerulaArborModVariables.MapVariables.get(world).traderSpawnRate = CaerulaArborModVariables.MapVariables.get(world).traderSpawnRate + 0.015;
					CaerulaArborModVariables.MapVariables.get(world).syncData(world);
				}
			}
		}
	}
}
