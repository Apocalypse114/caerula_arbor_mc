package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.display.StonecutterDollDisplayItem;

public class StonecutterDollDisplayModel extends GeoModel<StonecutterDollDisplayItem> {
	@Override
	public ResourceLocation getAnimationResource(StonecutterDollDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/chiseler_block.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(StonecutterDollDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/chiseler_block.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(StonecutterDollDisplayItem entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/chieslerfish.png");
	}
}
