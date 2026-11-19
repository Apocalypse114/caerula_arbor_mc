package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.XantisEntity;

public class XantisModel extends GeoModel<XantisEntity> {
	@Override
	public ResourceLocation getAnimationResource(XantisEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/xantis.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(XantisEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/xantis.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(XantisEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
