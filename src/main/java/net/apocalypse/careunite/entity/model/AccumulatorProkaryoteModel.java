package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.AccumulatorProkaryoteEntity;

public class AccumulatorProkaryoteModel extends GeoModel<AccumulatorProkaryoteEntity> {
	@Override
	public ResourceLocation getAnimationResource(AccumulatorProkaryoteEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/accumulator.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(AccumulatorProkaryoteEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/accumulator.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(AccumulatorProkaryoteEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
