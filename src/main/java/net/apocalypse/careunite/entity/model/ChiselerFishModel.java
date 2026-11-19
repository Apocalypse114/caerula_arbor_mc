package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.ChiselerFishEntity;

public class ChiselerFishModel extends GeoModel<ChiselerFishEntity> {
	@Override
	public ResourceLocation getAnimationResource(ChiselerFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/chiseler.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ChiselerFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/chiseler.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ChiselerFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
