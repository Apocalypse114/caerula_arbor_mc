package net.apocalypse.careunite.item.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.item.UninishedBeautyItem;

public class UninishedBeautyItemModel extends GeoModel<UninishedBeautyItem> {
	@Override
	public ResourceLocation getAnimationResource(UninishedBeautyItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/unfinished_beautuy.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(UninishedBeautyItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/unfinished_beautuy.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(UninishedBeautyItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/item/undone_beauty.png");
	}
}
