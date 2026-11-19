package net.apocalypse.careunite.block.listener;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.apocalypse.careunite.init.CaerulaArborModBlockEntities;
import net.apocalypse.careunite.block.renderer.ViviparousLilyTileRenderer;
import net.apocalypse.careunite.block.renderer.TrailriteArmorstandTileRenderer;
import net.apocalypse.careunite.block.renderer.TidewayCradleTileRenderer;
import net.apocalypse.careunite.block.renderer.SwarmcallerDollTileRenderer;
import net.apocalypse.careunite.block.renderer.StonecutterDollTileRenderer;
import net.apocalypse.careunite.block.renderer.PocketSeaDollTileRenderer;
import net.apocalypse.careunite.block.renderer.MizukiStatueTileRenderer;
import net.apocalypse.careunite.block.renderer.LivingArmorstandTileRenderer;
import net.apocalypse.careunite.block.renderer.IllusionerBannerTileRenderer;
import net.apocalypse.careunite.block.renderer.HugeLilyTileRenderer;
import net.apocalypse.careunite.block.renderer.HighmoreSpawningBlockTileRenderer;
import net.apocalypse.careunite.block.renderer.HighmoreSpawnblockTileRenderer;
import net.apocalypse.careunite.block.renderer.CrisisTableTileRenderer;
import net.apocalypse.careunite.block.renderer.ChestmegaSpawnerTileRenderer;
import net.apocalypse.careunite.block.renderer.CentrifugerTileRenderer;
import net.apocalypse.careunite.block.renderer.AbandonedSulptureTileRenderer;
import net.apocalypse.careunite.CaerulaArborMod;

@Mod.EventBusSubscriber(modid = CaerulaArborMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientListener {
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(CaerulaArborModBlockEntities.TIDEWAY_CRADLE.get(), context -> new TidewayCradleTileRenderer());
		event.registerBlockEntityRenderer(CaerulaArborModBlockEntities.CHESTMEGA_SPAWNER.get(), context -> new ChestmegaSpawnerTileRenderer());
		event.registerBlockEntityRenderer(CaerulaArborModBlockEntities.VIVIPAROUS_LILY.get(), context -> new ViviparousLilyTileRenderer());
		event.registerBlockEntityRenderer(CaerulaArborModBlockEntities.HUGE_LILY.get(), context -> new HugeLilyTileRenderer());
		event.registerBlockEntityRenderer(CaerulaArborModBlockEntities.HIGHMORE_SPAWNBLOCK.get(), context -> new HighmoreSpawnblockTileRenderer());
		event.registerBlockEntityRenderer(CaerulaArborModBlockEntities.CRISIS_TABLE.get(), context -> new CrisisTableTileRenderer());
		event.registerBlockEntityRenderer(CaerulaArborModBlockEntities.HIGHMORE_SPAWNING_BLOCK.get(), context -> new HighmoreSpawningBlockTileRenderer());
		event.registerBlockEntityRenderer(CaerulaArborModBlockEntities.MIZUKI_STATUE.get(), context -> new MizukiStatueTileRenderer());
		event.registerBlockEntityRenderer(CaerulaArborModBlockEntities.POCKET_SEA_DOLL.get(), context -> new PocketSeaDollTileRenderer());
		event.registerBlockEntityRenderer(CaerulaArborModBlockEntities.SWARMCALLER_DOLL.get(), context -> new SwarmcallerDollTileRenderer());
		event.registerBlockEntityRenderer(CaerulaArborModBlockEntities.STONECUTTER_DOLL.get(), context -> new StonecutterDollTileRenderer());
		event.registerBlockEntityRenderer(CaerulaArborModBlockEntities.ABANDONED_SULPTURE.get(), context -> new AbandonedSulptureTileRenderer());
		event.registerBlockEntityRenderer(CaerulaArborModBlockEntities.CENTRIFUGER.get(), context -> new CentrifugerTileRenderer());
		event.registerBlockEntityRenderer(CaerulaArborModBlockEntities.ILLUSIONER_BANNER.get(), context -> new IllusionerBannerTileRenderer());
		event.registerBlockEntityRenderer(CaerulaArborModBlockEntities.LIVING_ARMORSTAND.get(), context -> new LivingArmorstandTileRenderer());
		event.registerBlockEntityRenderer(CaerulaArborModBlockEntities.TRAILRITE_ARMORSTAND.get(), context -> new TrailriteArmorstandTileRenderer());
	}
}
