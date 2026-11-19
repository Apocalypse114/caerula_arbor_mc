package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.QunyouWantedIsharmlaEntity;

public class QunyouWantedIsharmlaModel extends GeoModel<QunyouWantedIsharmlaEntity> {
	@Override
	public ResourceLocation getAnimationResource(QunyouWantedIsharmlaEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/isharmla.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(QunyouWantedIsharmlaEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/isharmla.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(QunyouWantedIsharmlaEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
