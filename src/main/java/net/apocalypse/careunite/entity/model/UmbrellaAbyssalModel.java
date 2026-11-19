package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.UmbrellaAbyssalEntity;

public class UmbrellaAbyssalModel extends GeoModel<UmbrellaAbyssalEntity> {
	@Override
	public ResourceLocation getAnimationResource(UmbrellaAbyssalEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/umbrella.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(UmbrellaAbyssalEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/umbrella.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(UmbrellaAbyssalEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
