package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.SpecterDollEntity;

public class SpecterDollModel extends GeoModel<SpecterDollEntity> {
	@Override
	public ResourceLocation getAnimationResource(SpecterDollEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/specter_doll.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SpecterDollEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/specter_doll.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SpecterDollEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
