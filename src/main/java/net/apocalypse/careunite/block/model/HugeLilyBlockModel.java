package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.entity.HugeLilyTileEntity;

public class HugeLilyBlockModel extends GeoModel<HugeLilyTileEntity> {
	@Override
	public ResourceLocation getAnimationResource(HugeLilyTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/viviparous_lily_huge.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(HugeLilyTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/viviparous_lily_huge.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(HugeLilyTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/vivi_lily_huge.png");
	}
}
