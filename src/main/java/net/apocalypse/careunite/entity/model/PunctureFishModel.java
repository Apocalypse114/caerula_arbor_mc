package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.PunctureFishEntity;

public class PunctureFishModel extends GeoModel<PunctureFishEntity> {
	@Override
	public ResourceLocation getAnimationResource(PunctureFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/puncturefish.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(PunctureFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/puncturefish.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PunctureFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
