package net.apocalypse.careunite.item.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.item.WavecleaverItem;

public class WavecleaverItemModel extends GeoModel<WavecleaverItem> {
	@Override
	public ResourceLocation getAnimationResource(WavecleaverItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/wavelceaver.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(WavecleaverItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/wavelceaver.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(WavecleaverItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/item/weavecleaver.png");
	}
}
