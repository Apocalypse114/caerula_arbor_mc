package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.ReaperFishEntity;

public class ReaperFishModel extends GeoModel<ReaperFishEntity> {
	@Override
	public ResourceLocation getAnimationResource(ReaperFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/reaperfish.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ReaperFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/reaperfish.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ReaperFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
