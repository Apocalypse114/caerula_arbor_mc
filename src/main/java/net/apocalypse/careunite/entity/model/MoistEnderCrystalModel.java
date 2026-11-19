package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.MoistEnderCrystalEntity;

public class MoistEnderCrystalModel extends GeoModel<MoistEnderCrystalEntity> {
	@Override
	public ResourceLocation getAnimationResource(MoistEnderCrystalEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/moist_crystal.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(MoistEnderCrystalEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/moist_crystal.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MoistEnderCrystalEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
