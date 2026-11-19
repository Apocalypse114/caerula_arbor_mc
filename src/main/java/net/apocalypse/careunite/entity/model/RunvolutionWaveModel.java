package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.RunvolutionWaveEntity;

public class RunvolutionWaveModel extends GeoModel<RunvolutionWaveEntity> {
	@Override
	public ResourceLocation getAnimationResource(RunvolutionWaveEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/runvolution_wave.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(RunvolutionWaveEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/runvolution_wave.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RunvolutionWaveEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
