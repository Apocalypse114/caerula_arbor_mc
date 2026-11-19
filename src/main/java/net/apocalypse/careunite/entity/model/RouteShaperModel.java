package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.RouteShaperEntity;

public class RouteShaperModel extends GeoModel<RouteShaperEntity> {
	@Override
	public ResourceLocation getAnimationResource(RouteShaperEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/routeshaper.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(RouteShaperEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/routeshaper.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RouteShaperEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
