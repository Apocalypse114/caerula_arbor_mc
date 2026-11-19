package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.RunvolutionEntity;

public class RunvolutionModel extends GeoModel<RunvolutionEntity> {
	@Override
	public ResourceLocation getAnimationResource(RunvolutionEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/runvolution.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(RunvolutionEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/runvolution.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RunvolutionEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
