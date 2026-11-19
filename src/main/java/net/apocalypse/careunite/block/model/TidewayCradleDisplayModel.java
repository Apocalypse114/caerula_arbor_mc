package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.display.TidewayCradleDisplayItem;

public class TidewayCradleDisplayModel extends GeoModel<TidewayCradleDisplayItem> {
	@Override
	public ResourceLocation getAnimationResource(TidewayCradleDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/tideway_cradle.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TidewayCradleDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/tideway_cradle.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TidewayCradleDisplayItem entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/tideway_cradle.png");
	}
}
