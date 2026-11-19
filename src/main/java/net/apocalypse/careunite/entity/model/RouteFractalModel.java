package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.RouteFractalEntity;

public class RouteFractalModel extends GeoModel<RouteFractalEntity> {
	@Override
	public ResourceLocation getAnimationResource(RouteFractalEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/routeshaper.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(RouteFractalEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/routeshaper.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RouteFractalEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
