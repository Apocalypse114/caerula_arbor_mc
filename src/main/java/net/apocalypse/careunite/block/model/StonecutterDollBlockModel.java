package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.entity.StonecutterDollTileEntity;

public class StonecutterDollBlockModel extends GeoModel<StonecutterDollTileEntity> {
	@Override
	public ResourceLocation getAnimationResource(StonecutterDollTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/chiseler_block.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(StonecutterDollTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/chiseler_block.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(StonecutterDollTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/chieslerfish.png");
	}
}
