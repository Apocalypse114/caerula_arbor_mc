package net.apocalypse.careunite.item.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.item.CircularSawItem;

public class CircularSawItemModel extends GeoModel<CircularSawItem> {
	@Override
	public ResourceLocation getAnimationResource(CircularSawItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/circular_saw.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(CircularSawItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/circular_saw.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CircularSawItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/item/saw.png");
	}
}
