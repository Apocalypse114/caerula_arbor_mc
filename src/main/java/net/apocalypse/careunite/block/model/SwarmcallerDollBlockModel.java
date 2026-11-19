package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.entity.SwarmcallerDollTileEntity;

public class SwarmcallerDollBlockModel extends GeoModel<SwarmcallerDollTileEntity> {
	@Override
	public ResourceLocation getAnimationResource(SwarmcallerDollTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/umbrella_block.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SwarmcallerDollTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/umbrella_block.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SwarmcallerDollTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/umbrella.png");
	}
}
