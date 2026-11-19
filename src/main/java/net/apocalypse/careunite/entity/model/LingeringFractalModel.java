package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.LingeringFractalEntity;

public class LingeringFractalModel extends GeoModel<LingeringFractalEntity> {
	@Override
	public ResourceLocation getAnimationResource(LingeringFractalEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/routeshaper.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(LingeringFractalEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/routeshaper.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(LingeringFractalEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
