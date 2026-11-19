package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.IzumilDollEntity;

public class IzumilDollModel extends GeoModel<IzumilDollEntity> {
	@Override
	public ResourceLocation getAnimationResource(IzumilDollEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/izumik_doll.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(IzumilDollEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/izumik_doll.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(IzumilDollEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
