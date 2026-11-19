package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.Al1SHelperEntity;

public class Al1SHelperModel extends GeoModel<Al1SHelperEntity> {
	@Override
	public ResourceLocation getAnimationResource(Al1SHelperEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/little_helper.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(Al1SHelperEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/little_helper.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Al1SHelperEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
