package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.DepositerProkaryoteEntity;

public class DepositerProkaryoteModel extends GeoModel<DepositerProkaryoteEntity> {
	@Override
	public ResourceLocation getAnimationResource(DepositerProkaryoteEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/depsoiter.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(DepositerProkaryoteEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/depsoiter.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DepositerProkaryoteEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
