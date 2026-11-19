package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.NetherseaSlimeEntity;

public class NetherseaSlimeModel extends GeoModel<NetherseaSlimeEntity> {
	@Override
	public ResourceLocation getAnimationResource(NetherseaSlimeEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/nethersea_slime.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(NetherseaSlimeEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/nethersea_slime.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(NetherseaSlimeEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
