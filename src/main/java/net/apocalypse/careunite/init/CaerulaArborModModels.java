
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.apocalypse.careunite.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.apocalypse.careunite.client.model.Modelwither_slash;
import net.apocalypse.careunite.client.model.Modeltypheous_sign;
import net.apocalypse.careunite.client.model.Modelsealeather_chitin_armor;
import net.apocalypse.careunite.client.model.Modelraid_sign;
import net.apocalypse.careunite.client.model.Modeloceanarrow_model;
import net.apocalypse.careunite.client.model.Modelmonstrosity_rocket;
import net.apocalypse.careunite.client.model.Modelmaster_alchemist;
import net.apocalypse.careunite.client.model.Modellarge_arrow;
import net.apocalypse.careunite.client.model.Modelinquisition_suit;
import net.apocalypse.careunite.client.model.Modelinquisition_armor;
import net.apocalypse.careunite.client.model.Modelhighmore_shoot;
import net.apocalypse.careunite.client.model.Modelfleefish;
import net.apocalypse.careunite.client.model.Modelfishbullet_Converted;
import net.apocalypse.careunite.client.model.Modelfakebullet;
import net.apocalypse.careunite.client.model.Modelchitin_trident_thrown;
import net.apocalypse.careunite.client.model.Modelbee_stinger;
import net.apocalypse.careunite.client.model.Modelbeacon_sign;
import net.apocalypse.careunite.client.model.Modelbatalion;
import net.apocalypse.careunite.client.model.Modelanchor_52_fly;
import net.apocalypse.careunite.client.model.ModelTrailriteTrident;
import net.apocalypse.careunite.client.model.ModelTombstoneVariantDisplay;
import net.apocalypse.careunite.client.model.ModelTombstoneUndertidesSmall;
import net.apocalypse.careunite.client.model.ModelTombstoneUndertides;
import net.apocalypse.careunite.client.model.ModelTombstoneStultiferaSmall;
import net.apocalypse.careunite.client.model.ModelTombstoneStultifera;
import net.apocalypse.careunite.client.model.ModelOceanizedSnifferEgg;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class CaerulaArborModModels {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(Modelbatalion.LAYER_LOCATION, Modelbatalion::createBodyLayer);
		event.registerLayerDefinition(ModelTombstoneVariantDisplay.LAYER_LOCATION, ModelTombstoneVariantDisplay::createBodyLayer);
		event.registerLayerDefinition(Modelanchor_52_fly.LAYER_LOCATION, Modelanchor_52_fly::createBodyLayer);
		event.registerLayerDefinition(Modelsealeather_chitin_armor.LAYER_LOCATION, Modelsealeather_chitin_armor::createBodyLayer);
		event.registerLayerDefinition(Modelchitin_trident_thrown.LAYER_LOCATION, Modelchitin_trident_thrown::createBodyLayer);
		event.registerLayerDefinition(Modeltypheous_sign.LAYER_LOCATION, Modeltypheous_sign::createBodyLayer);
		event.registerLayerDefinition(Modelbeacon_sign.LAYER_LOCATION, Modelbeacon_sign::createBodyLayer);
		event.registerLayerDefinition(Modelfakebullet.LAYER_LOCATION, Modelfakebullet::createBodyLayer);
		event.registerLayerDefinition(Modelmonstrosity_rocket.LAYER_LOCATION, Modelmonstrosity_rocket::createBodyLayer);
		event.registerLayerDefinition(Modeloceanarrow_model.LAYER_LOCATION, Modeloceanarrow_model::createBodyLayer);
		event.registerLayerDefinition(Modelwither_slash.LAYER_LOCATION, Modelwither_slash::createBodyLayer);
		event.registerLayerDefinition(ModelTrailriteTrident.LAYER_LOCATION, ModelTrailriteTrident::createBodyLayer);
		event.registerLayerDefinition(Modelbee_stinger.LAYER_LOCATION, Modelbee_stinger::createBodyLayer);
		event.registerLayerDefinition(Modelinquisition_armor.LAYER_LOCATION, Modelinquisition_armor::createBodyLayer);
		event.registerLayerDefinition(Modelfishbullet_Converted.LAYER_LOCATION, Modelfishbullet_Converted::createBodyLayer);
		event.registerLayerDefinition(ModelTombstoneUndertides.LAYER_LOCATION, ModelTombstoneUndertides::createBodyLayer);
		event.registerLayerDefinition(Modelinquisition_suit.LAYER_LOCATION, Modelinquisition_suit::createBodyLayer);
		event.registerLayerDefinition(Modellarge_arrow.LAYER_LOCATION, Modellarge_arrow::createBodyLayer);
		event.registerLayerDefinition(ModelTombstoneUndertidesSmall.LAYER_LOCATION, ModelTombstoneUndertidesSmall::createBodyLayer);
		event.registerLayerDefinition(Modelraid_sign.LAYER_LOCATION, Modelraid_sign::createBodyLayer);
		event.registerLayerDefinition(Modelhighmore_shoot.LAYER_LOCATION, Modelhighmore_shoot::createBodyLayer);
		event.registerLayerDefinition(Modelfleefish.LAYER_LOCATION, Modelfleefish::createBodyLayer);
		event.registerLayerDefinition(ModelOceanizedSnifferEgg.LAYER_LOCATION, ModelOceanizedSnifferEgg::createBodyLayer);
		event.registerLayerDefinition(Modelmaster_alchemist.LAYER_LOCATION, Modelmaster_alchemist::createBodyLayer);
		event.registerLayerDefinition(ModelTombstoneStultifera.LAYER_LOCATION, ModelTombstoneStultifera::createBodyLayer);
		event.registerLayerDefinition(ModelTombstoneStultiferaSmall.LAYER_LOCATION, ModelTombstoneStultiferaSmall::createBodyLayer);
	}
}
