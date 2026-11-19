package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.FeederProkaryoteEntity;

public class FeederProkaryoteModel extends GeoModel<FeederProkaryoteEntity> {
	@Override
	public ResourceLocation getAnimationResource(FeederProkaryoteEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/feeder.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(FeederProkaryoteEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/feeder.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(FeederProkaryoteEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
