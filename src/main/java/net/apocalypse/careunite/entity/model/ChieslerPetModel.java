package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.ChieslerPetEntity;

public class ChieslerPetModel extends GeoModel<ChieslerPetEntity> {
	@Override
	public ResourceLocation getAnimationResource(ChieslerPetEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/chiseler.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ChieslerPetEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/chiseler.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ChieslerPetEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
