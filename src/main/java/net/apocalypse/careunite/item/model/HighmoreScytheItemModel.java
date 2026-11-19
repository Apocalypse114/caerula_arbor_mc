package net.apocalypse.careunite.item.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.item.HighmoreScytheItem;

public class HighmoreScytheItemModel extends GeoModel<HighmoreScytheItem> {
	@Override
	public ResourceLocation getAnimationResource(HighmoreScytheItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/highmorescythe.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(HighmoreScytheItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/highmorescythe.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(HighmoreScytheItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/item/highmore_scythe_bladed.png");
	}
}
