package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.IzumikEntity;

public class IzumikModel extends GeoModel<IzumikEntity> {
	@Override
	public ResourceLocation getAnimationResource(IzumikEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/izumik.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(IzumikEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/izumik.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(IzumikEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
