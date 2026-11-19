package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.display.HugeLilyDisplayItem;

public class HugeLilyDisplayModel extends GeoModel<HugeLilyDisplayItem> {
	@Override
	public ResourceLocation getAnimationResource(HugeLilyDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/viviparous_lily_huge.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(HugeLilyDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/viviparous_lily_huge.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(HugeLilyDisplayItem entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/vivi_lily_huge.png");
	}
}
