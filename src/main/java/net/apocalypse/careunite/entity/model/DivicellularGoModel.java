package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.DivicellularGoEntity;

public class DivicellularGoModel extends GeoModel<DivicellularGoEntity> {
	@Override
	public ResourceLocation getAnimationResource(DivicellularGoEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/accumulator_pet.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(DivicellularGoEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/accumulator_pet.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DivicellularGoEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
