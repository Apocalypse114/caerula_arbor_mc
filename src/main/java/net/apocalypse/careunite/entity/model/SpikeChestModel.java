package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.SpikeChestEntity;

public class SpikeChestModel extends GeoModel<SpikeChestEntity> {
	@Override
	public ResourceLocation getAnimationResource(SpikeChestEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/spike_chest.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SpikeChestEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/spike_chest.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SpikeChestEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
