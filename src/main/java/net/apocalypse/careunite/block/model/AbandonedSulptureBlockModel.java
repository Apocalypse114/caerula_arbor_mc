package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.entity.AbandonedSulptureTileEntity;

public class AbandonedSulptureBlockModel extends GeoModel<AbandonedSulptureTileEntity> {
	@Override
	public ResourceLocation getAnimationResource(AbandonedSulptureTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/abandoned_sulpture.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(AbandonedSulptureTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/abandoned_sulpture.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(AbandonedSulptureTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/theabandoned_sculpture.png");
	}
}
