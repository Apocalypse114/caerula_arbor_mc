package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.SliderFishEntity;

public class SliderFishModel extends GeoModel<SliderFishEntity> {
	@Override
	public ResourceLocation getAnimationResource(SliderFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/slidingfish.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SliderFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/slidingfish.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SliderFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
