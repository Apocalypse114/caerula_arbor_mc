package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.OceanizedSilverfishEntity;

public class OceanizedSilverfishModel extends GeoModel<OceanizedSilverfishEntity> {
	@Override
	public ResourceLocation getAnimationResource(OceanizedSilverfishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/oceanized_silverfish.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(OceanizedSilverfishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/oceanized_silverfish.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(OceanizedSilverfishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
