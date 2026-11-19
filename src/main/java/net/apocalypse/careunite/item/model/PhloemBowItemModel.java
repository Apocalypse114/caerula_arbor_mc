package net.apocalypse.careunite.item.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.item.PhloemBowItem;

public class PhloemBowItemModel extends GeoModel<PhloemBowItem> {
	@Override
	public ResourceLocation getAnimationResource(PhloemBowItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/pholemnbow.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(PhloemBowItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/pholemnbow.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PhloemBowItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/item/combinedarrow.png");
	}
}
