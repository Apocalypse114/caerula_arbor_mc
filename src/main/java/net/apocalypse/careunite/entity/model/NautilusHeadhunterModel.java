package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.NautilusHeadhunterEntity;

public class NautilusHeadhunterModel extends GeoModel<NautilusHeadhunterEntity> {
	@Override
	public ResourceLocation getAnimationResource(NautilusHeadhunterEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/nultilus_headhunter.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(NautilusHeadhunterEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/nultilus_headhunter.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(NautilusHeadhunterEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
