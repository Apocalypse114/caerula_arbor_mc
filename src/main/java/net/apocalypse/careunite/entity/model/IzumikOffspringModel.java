package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.IzumikOffspringEntity;

public class IzumikOffspringModel extends GeoModel<IzumikOffspringEntity> {
	@Override
	public ResourceLocation getAnimationResource(IzumikOffspringEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/izumik_offspring.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(IzumikOffspringEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/izumik_offspring.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(IzumikOffspringEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
