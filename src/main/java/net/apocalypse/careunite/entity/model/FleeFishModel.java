package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.FleeFishEntity;

public class FleeFishModel extends GeoModel<FleeFishEntity> {
	@Override
	public ResourceLocation getAnimationResource(FleeFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/skimming_sea_drifter.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(FleeFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/skimming_sea_drifter.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(FleeFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
