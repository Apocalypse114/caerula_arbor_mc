package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.display.SwarmcallerDollDisplayItem;

public class SwarmcallerDollDisplayModel extends GeoModel<SwarmcallerDollDisplayItem> {
	@Override
	public ResourceLocation getAnimationResource(SwarmcallerDollDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/umbrella_block.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SwarmcallerDollDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/umbrella_block.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SwarmcallerDollDisplayItem entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/umbrella.png");
	}
}
