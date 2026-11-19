package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.entity.TidewayCradleTileEntity;

public class TidewayCradleBlockModel extends GeoModel<TidewayCradleTileEntity> {
	@Override
	public ResourceLocation getAnimationResource(TidewayCradleTileEntity animatable) {
		final int blockstate = animatable.blockstateNew;
		if (blockstate == 1)
			return new ResourceLocation("caerula_arbor_reunite", "animations/tideway_cradle.animation.json");
		return new ResourceLocation("caerula_arbor_reunite", "animations/tideway_cradle.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TidewayCradleTileEntity animatable) {
		final int blockstate = animatable.blockstateNew;
		if (blockstate == 1)
			return new ResourceLocation("caerula_arbor_reunite", "geo/tideway_cradle.geo.json");
		return new ResourceLocation("caerula_arbor_reunite", "geo/tideway_cradle.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TidewayCradleTileEntity animatable) {
		final int blockstate = animatable.blockstateNew;
		if (blockstate == 1)
			return new ResourceLocation("caerula_arbor_reunite", "textures/block/cradle_dim.png");
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/tideway_cradle.png");
	}
}
