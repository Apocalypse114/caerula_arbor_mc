package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.SuperSliderEntity;

public class SuperSliderModel extends GeoModel<SuperSliderEntity> {
	@Override
	public ResourceLocation getAnimationResource(SuperSliderEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/slidingfish.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SuperSliderEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/slidingfish.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SuperSliderEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
