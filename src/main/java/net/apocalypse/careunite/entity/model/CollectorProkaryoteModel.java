package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.CollectorProkaryoteEntity;

public class CollectorProkaryoteModel extends GeoModel<CollectorProkaryoteEntity> {
	@Override
	public ResourceLocation getAnimationResource(CollectorProkaryoteEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/collector.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(CollectorProkaryoteEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/collector.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CollectorProkaryoteEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
