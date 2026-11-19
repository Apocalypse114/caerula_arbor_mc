package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.MegaChestEntity;

public class MegaChestModel extends GeoModel<MegaChestEntity> {
	@Override
	public ResourceLocation getAnimationResource(MegaChestEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/chestmega.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(MegaChestEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/chestmega.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MegaChestEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
