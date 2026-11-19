package net.apocalypse.careunite.item.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.item.WearableCrownItem;

public class WearableCrownModel extends GeoModel<WearableCrownItem> {
	@Override
	public ResourceLocation getAnimationResource(WearableCrownItem object) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/crown.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(WearableCrownItem object) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/crown.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(WearableCrownItem object) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/item/crown.png");
	}
}
