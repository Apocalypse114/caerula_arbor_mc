package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.TideutantRockSpiderEntity;

public class TideutantRockSpiderModel extends GeoModel<TideutantRockSpiderEntity> {
	@Override
	public ResourceLocation getAnimationResource(TideutantRockSpiderEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/tidutant_rock_spider.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TideutantRockSpiderEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/tidutant_rock_spider.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TideutantRockSpiderEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
