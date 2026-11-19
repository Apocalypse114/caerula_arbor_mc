package net.apocalypse.careunite.item.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.item.LegendarySpearItem;

public class LegendarySpearItemModel extends GeoModel<LegendarySpearItem> {
	@Override
	public ResourceLocation getAnimationResource(LegendarySpearItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/lengdendaryspear.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(LegendarySpearItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/lengdendaryspear.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(LegendarySpearItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/item/superchitinspear.png");
	}
}
