package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.display.PocketSeaDollDisplayItem;

public class PocketSeaDollDisplayModel extends GeoModel<PocketSeaDollDisplayItem> {
	@Override
	public ResourceLocation getAnimationResource(PocketSeaDollDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/creeper_fish_block.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(PocketSeaDollDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/creeper_fish_block.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PocketSeaDollDisplayItem entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/creeperfish.png");
	}
}
