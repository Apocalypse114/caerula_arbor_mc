package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.entity.IllusionerBannerTileEntity;

public class IllusionerBannerBlockModel extends GeoModel<IllusionerBannerTileEntity> {
	@Override
	public ResourceLocation getAnimationResource(IllusionerBannerTileEntity animatable) {
		final int blockstate = animatable.blockstateNew;
		if (blockstate == 1)
			return new ResourceLocation("caerula_arbor_reunite", "animations/oceanized_banner.animation.json");
		return new ResourceLocation("caerula_arbor_reunite", "animations/oceanized_banner.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(IllusionerBannerTileEntity animatable) {
		final int blockstate = animatable.blockstateNew;
		if (blockstate == 1)
			return new ResourceLocation("caerula_arbor_reunite", "geo/oceanized_banner.geo.json");
		return new ResourceLocation("caerula_arbor_reunite", "geo/oceanized_banner.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(IllusionerBannerTileEntity animatable) {
		final int blockstate = animatable.blockstateNew;
		if (blockstate == 1)
			return new ResourceLocation("caerula_arbor_reunite", "textures/block/illusioner_banner.png");
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/illusioner_banner.png");
	}
}
