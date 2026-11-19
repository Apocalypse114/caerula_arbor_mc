package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.IsharmlaTearEntity;

public class IsharmlaTearModel extends GeoModel<IsharmlaTearEntity> {
	@Override
	public ResourceLocation getAnimationResource(IsharmlaTearEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/isharmla_tear.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(IsharmlaTearEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/isharmla_tear.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(IsharmlaTearEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
