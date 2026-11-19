package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.CaerulaOffspringEntity;

public class CaerulaOffspringModel extends GeoModel<CaerulaOffspringEntity> {
	@Override
	public ResourceLocation getAnimationResource(CaerulaOffspringEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/izumik_offspring.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(CaerulaOffspringEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/izumik_offspring.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CaerulaOffspringEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
