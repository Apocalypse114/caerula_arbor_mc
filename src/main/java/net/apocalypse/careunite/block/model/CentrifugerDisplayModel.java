package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.display.CentrifugerDisplayItem;

public class CentrifugerDisplayModel extends GeoModel<CentrifugerDisplayItem> {
	@Override
	public ResourceLocation getAnimationResource(CentrifugerDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/centrifuger.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(CentrifugerDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/centrifuger.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CentrifugerDisplayItem entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/centrifuger.png");
	}
}
