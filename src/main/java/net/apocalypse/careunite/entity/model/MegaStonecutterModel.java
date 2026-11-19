package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.MegaStonecutterEntity;

public class MegaStonecutterModel extends GeoModel<MegaStonecutterEntity> {
	@Override
	public ResourceLocation getAnimationResource(MegaStonecutterEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/mega_stonecutter.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(MegaStonecutterEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/mega_stonecutter.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MegaStonecutterEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
