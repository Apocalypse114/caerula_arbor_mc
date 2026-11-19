package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.display.IllusionerBannerDisplayItem;

public class IllusionerBannerDisplayModel extends GeoModel<IllusionerBannerDisplayItem> {
	@Override
	public ResourceLocation getAnimationResource(IllusionerBannerDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/oceanized_banner.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(IllusionerBannerDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/oceanized_banner.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(IllusionerBannerDisplayItem entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/illusioner_banner.png");
	}
}
