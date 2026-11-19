package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.TombstoneLivingEntity;

public class TombstoneLivingModel extends GeoModel<TombstoneLivingEntity> {
	@Override
	public ResourceLocation getAnimationResource(TombstoneLivingEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/tombstone_living.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TombstoneLivingEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/tombstone_living.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TombstoneLivingEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
