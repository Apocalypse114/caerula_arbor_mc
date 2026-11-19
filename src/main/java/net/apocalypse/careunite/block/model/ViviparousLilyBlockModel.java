package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.entity.ViviparousLilyTileEntity;

public class ViviparousLilyBlockModel extends GeoModel<ViviparousLilyTileEntity> {
	@Override
	public ResourceLocation getAnimationResource(ViviparousLilyTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/viviparous_lily.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ViviparousLilyTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/viviparous_lily.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ViviparousLilyTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/vivi_lily.png");
	}
}
