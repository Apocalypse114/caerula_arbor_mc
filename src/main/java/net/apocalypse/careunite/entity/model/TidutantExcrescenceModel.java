package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.TidutantExcrescenceEntity;

public class TidutantExcrescenceModel extends GeoModel<TidutantExcrescenceEntity> {
	@Override
	public ResourceLocation getAnimationResource(TidutantExcrescenceEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/tidutant_excrescence.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TidutantExcrescenceEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/tidutant_excrescence.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TidutantExcrescenceEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
