package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.entity.PocketSeaDollTileEntity;

public class PocketSeaDollBlockModel extends GeoModel<PocketSeaDollTileEntity> {
	@Override
	public ResourceLocation getAnimationResource(PocketSeaDollTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/creeper_fish_block.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(PocketSeaDollTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/creeper_fish_block.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PocketSeaDollTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/creeperfish.png");
	}
}
