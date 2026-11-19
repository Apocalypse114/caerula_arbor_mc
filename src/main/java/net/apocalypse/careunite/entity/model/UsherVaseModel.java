package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.UsherVaseEntity;

public class UsherVaseModel extends GeoModel<UsherVaseEntity> {
	@Override
	public ResourceLocation getAnimationResource(UsherVaseEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/usher_vase.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(UsherVaseEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/usher_vase.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(UsherVaseEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
