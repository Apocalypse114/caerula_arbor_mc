package net.apocalypse.careunite.item.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.item.MartusBookItem;

public class MartusBookItemModel extends GeoModel<MartusBookItem> {
	@Override
	public ResourceLocation getAnimationResource(MartusBookItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/martus_book.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(MartusBookItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/martus_book.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MartusBookItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/item/martus_book.png");
	}
}
