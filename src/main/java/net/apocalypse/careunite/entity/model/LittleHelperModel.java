package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.LittleHelperEntity;

public class LittleHelperModel extends GeoModel<LittleHelperEntity> {
	@Override
	public ResourceLocation getAnimationResource(LittleHelperEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/little_helper.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(LittleHelperEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/little_helper.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(LittleHelperEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
