package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.LineringPathshaperEntity;

public class LineringPathshaperModel extends GeoModel<LineringPathshaperEntity> {
	@Override
	public ResourceLocation getAnimationResource(LineringPathshaperEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/routeshaper.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(LineringPathshaperEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/routeshaper.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(LineringPathshaperEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
