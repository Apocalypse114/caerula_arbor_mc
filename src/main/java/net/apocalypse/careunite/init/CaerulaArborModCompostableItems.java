
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.apocalypse.careunite.init;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.level.block.ComposterBlock;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CaerulaArborModCompostableItems {
	@SubscribeEvent
	public static void addComposterItems(FMLCommonSetupEvent event) {
		ComposterBlock.COMPOSTABLES.put(CaerulaArborModItems.SEA_TRAIL_MOR.get(), 0.2f);
		ComposterBlock.COMPOSTABLES.put(CaerulaArborModBlocks.SEA_TRAIL_SOLID.get().asItem(), 0.75f);
	}
}
