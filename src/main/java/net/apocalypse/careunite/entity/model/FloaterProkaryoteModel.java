package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.FloaterProkaryoteEntity;

public class FloaterProkaryoteModel extends GeoModel<FloaterProkaryoteEntity> {
	@Override
	public ResourceLocation getAnimationResource(FloaterProkaryoteEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/floater.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(FloaterProkaryoteEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/floater.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(FloaterProkaryoteEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
