package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.GladiiaWhirlEntity;

public class GladiiaWhirlModel extends GeoModel<GladiiaWhirlEntity> {
	@Override
	public ResourceLocation getAnimationResource(GladiiaWhirlEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/gladiia_whirl.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(GladiiaWhirlEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/gladiia_whirl.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(GladiiaWhirlEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
