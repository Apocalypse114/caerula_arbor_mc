package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.GuideAbyssalEntity;

public class GuideAbyssalModel extends GeoModel<GuideAbyssalEntity> {
	@Override
	public ResourceLocation getAnimationResource(GuideAbyssalEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/nethersea_brandguider.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(GuideAbyssalEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/nethersea_brandguider.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(GuideAbyssalEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
