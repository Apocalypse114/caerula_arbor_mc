package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.display.ViviparousLilyDisplayItem;

public class ViviparousLilyDisplayModel extends GeoModel<ViviparousLilyDisplayItem> {
	@Override
	public ResourceLocation getAnimationResource(ViviparousLilyDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/viviparous_lily.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ViviparousLilyDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/viviparous_lily.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ViviparousLilyDisplayItem entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/vivi_lily.png");
	}
}
