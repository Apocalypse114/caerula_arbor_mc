package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.BoneFishEntity;

public class BoneFishModel extends GeoModel<BoneFishEntity> {
	@Override
	public ResourceLocation getAnimationResource(BoneFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/bonefish.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(BoneFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/bonefish.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BoneFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
