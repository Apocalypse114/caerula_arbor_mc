package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.SonsEntity;

public class SonsModel extends GeoModel<SonsEntity> {
	@Override
	public ResourceLocation getAnimationResource(SonsEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/bishopson.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SonsEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/bishopson.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SonsEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
