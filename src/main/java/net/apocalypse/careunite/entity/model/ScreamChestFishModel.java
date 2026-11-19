package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.ScreamChestFishEntity;

public class ScreamChestFishModel extends GeoModel<ScreamChestFishEntity> {
	@Override
	public ResourceLocation getAnimationResource(ScreamChestFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/scream_chest_fish.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ScreamChestFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/scream_chest_fish.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ScreamChestFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
