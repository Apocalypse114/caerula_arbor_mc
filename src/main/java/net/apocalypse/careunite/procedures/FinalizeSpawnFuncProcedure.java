package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.living.MobSpawnEvent;

import net.apocalypse.careunite.CaerulaArborMod;

@Mod.EventBusSubscriber
public class FinalizeSpawnFuncProcedure {
	@SubscribeEvent
	public static void onSpawn(MobSpawnEvent.FinalizeSpawn event){
		if (event.isCanceled()) return;
        //CaerulaArborMod.LOGGER.info("SpawnType: {}", event.getSpawnType());
        ServerLevel world = event.getLevel().getLevel();
        Mob entity = event.getEntity();
        double x = event.getX(), y = event.getY(), z = event.getZ();
        BornFuncProcedure.execute(world, x, y, z, entity,
        	event.getSpawnType() == MobSpawnType.NATURAL || event.getSpawnType() == MobSpawnType.CHUNK_GENERATION);
    }
}
