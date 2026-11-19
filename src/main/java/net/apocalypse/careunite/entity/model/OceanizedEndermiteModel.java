package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.OceanizedEndermiteEntity;

public class OceanizedEndermiteModel extends GeoModel<OceanizedEndermiteEntity> {
	@Override
	public ResourceLocation getAnimationResource(OceanizedEndermiteEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/oceanzied_endermite.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(OceanizedEndermiteEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/oceanzied_endermite.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(OceanizedEndermiteEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
