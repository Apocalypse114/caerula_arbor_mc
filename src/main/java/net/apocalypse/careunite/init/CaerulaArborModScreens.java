
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.apocalypse.careunite.init;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.gui.screens.MenuScreens;

import net.apocalypse.careunite.client.gui.RelicShowcaseScreen;
import net.apocalypse.careunite.client.gui.PlayerEvoScreen;
import net.apocalypse.careunite.client.gui.InfoStrategySubsisScreen;
import net.apocalypse.careunite.client.gui.InfoStrategyMigrationScreen;
import net.apocalypse.careunite.client.gui.InfoStrategyGrowScreen;
import net.apocalypse.careunite.client.gui.InfoStrategyBreedScreen;
import net.apocalypse.careunite.client.gui.InfoStrategyAllScreen;
import net.apocalypse.careunite.client.gui.EvoTreeScreen;
import net.apocalypse.careunite.client.gui.EchoShowScreen;
import net.apocalypse.careunite.client.gui.DisoclutionShowScreen;
import net.apocalypse.careunite.client.gui.CentrifugerSelectScreen;
import net.apocalypse.careunite.client.gui.CaerulaRecordGUIScreen;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CaerulaArborModScreens {
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(CaerulaArborModMenus.CAERULA_RECORD_GUI.get(), CaerulaRecordGUIScreen::new);
			MenuScreens.register(CaerulaArborModMenus.RELIC_SHOWCASE.get(), RelicShowcaseScreen::new);
			MenuScreens.register(CaerulaArborModMenus.INFO_STRATEGY_SUBSIS.get(), InfoStrategySubsisScreen::new);
			MenuScreens.register(CaerulaArborModMenus.INFO_STRATEGY_BREED.get(), InfoStrategyBreedScreen::new);
			MenuScreens.register(CaerulaArborModMenus.INFO_STRATEGY_MIGRATION.get(), InfoStrategyMigrationScreen::new);
			MenuScreens.register(CaerulaArborModMenus.INFO_STRATEGY_GROW.get(), InfoStrategyGrowScreen::new);
			MenuScreens.register(CaerulaArborModMenus.INFO_STRATEGY_ALL.get(), InfoStrategyAllScreen::new);
			MenuScreens.register(CaerulaArborModMenus.EVO_TREE.get(), EvoTreeScreen::new);
			MenuScreens.register(CaerulaArborModMenus.CENTRIFUGER_SELECT.get(), CentrifugerSelectScreen::new);
			MenuScreens.register(CaerulaArborModMenus.PLAYER_EVO.get(), PlayerEvoScreen::new);
			MenuScreens.register(CaerulaArborModMenus.DISOCLUTION_SHOW.get(), DisoclutionShowScreen::new);
			MenuScreens.register(CaerulaArborModMenus.ECHO_SHOW.get(), EchoShowScreen::new);
		});
	}
}
