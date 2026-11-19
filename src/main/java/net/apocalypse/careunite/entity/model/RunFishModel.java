package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.RunFishEntity;

public class RunFishModel extends GeoModel<RunFishEntity> {
	@Override
	public ResourceLocation getAnimationResource(RunFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/shell_sea_runner.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(RunFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/shell_sea_runner.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RunFishEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
