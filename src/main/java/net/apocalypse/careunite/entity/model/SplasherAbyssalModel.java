package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.SplasherAbyssalEntity;

public class SplasherAbyssalModel extends GeoModel<SplasherAbyssalEntity> {
	@Override
	public ResourceLocation getAnimationResource(SplasherAbyssalEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/splasher.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SplasherAbyssalEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/splasher.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SplasherAbyssalEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
