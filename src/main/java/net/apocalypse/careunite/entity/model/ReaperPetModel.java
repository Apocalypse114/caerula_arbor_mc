package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.ReaperPetEntity;

public class ReaperPetModel extends GeoModel<ReaperPetEntity> {
	@Override
	public ResourceLocation getAnimationResource(ReaperPetEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/reaperpet.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ReaperPetEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/reaperpet.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ReaperPetEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
