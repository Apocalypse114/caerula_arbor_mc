package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.display.AbandonedSulptureDisplayItem;

public class AbandonedSulptureDisplayModel extends GeoModel<AbandonedSulptureDisplayItem> {
	@Override
	public ResourceLocation getAnimationResource(AbandonedSulptureDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/abandoned_sulpture.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(AbandonedSulptureDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/abandoned_sulpture.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(AbandonedSulptureDisplayItem entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/theabandoned_sculpture.png");
	}
}
