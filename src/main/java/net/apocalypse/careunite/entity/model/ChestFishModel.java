package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.ChestFishEntity;

public class ChestFishModel extends GeoModel<ChestFishEntity> {
	@Override
	public ResourceLocation getAnimationResource(ChestFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/chest_fish.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ChestFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/chest_fish.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ChestFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
