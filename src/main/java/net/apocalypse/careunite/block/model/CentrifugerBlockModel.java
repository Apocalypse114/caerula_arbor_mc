package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.entity.CentrifugerTileEntity;

public class CentrifugerBlockModel extends GeoModel<CentrifugerTileEntity> {
	@Override
	public ResourceLocation getAnimationResource(CentrifugerTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/centrifuger.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(CentrifugerTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/centrifuger.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CentrifugerTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/centrifuger.png");
	}
}
