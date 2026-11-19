package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.ThirsterEntity;

public class ThirsterModel extends GeoModel<ThirsterEntity> {
	@Override
	public ResourceLocation getAnimationResource(ThirsterEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/thirster.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ThirsterEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/thirster.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ThirsterEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
