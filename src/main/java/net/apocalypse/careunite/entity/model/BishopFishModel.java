package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.BishopFishEntity;

public class BishopFishModel extends GeoModel<BishopFishEntity> {
	@Override
	public ResourceLocation getAnimationResource(BishopFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/bishop.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(BishopFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/bishop.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BishopFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
