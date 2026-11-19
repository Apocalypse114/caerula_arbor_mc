package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.entity.MizukiStatueTileEntity;

public class MizukiStatueBlockModel extends GeoModel<MizukiStatueTileEntity> {
	@Override
	public ResourceLocation getAnimationResource(MizukiStatueTileEntity animatable) {
		final int blockstate = animatable.blockstateNew;
		if (blockstate == 1)
			return new ResourceLocation("caerula_arbor_reunite", "animations/mizuki.animation.json");
		return new ResourceLocation("caerula_arbor_reunite", "animations/mizuki.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(MizukiStatueTileEntity animatable) {
		final int blockstate = animatable.blockstateNew;
		if (blockstate == 1)
			return new ResourceLocation("caerula_arbor_reunite", "geo/mizuki.geo.json");
		return new ResourceLocation("caerula_arbor_reunite", "geo/mizuki.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MizukiStatueTileEntity animatable) {
		final int blockstate = animatable.blockstateNew;
		if (blockstate == 1)
			return new ResourceLocation("caerula_arbor_reunite", "textures/block/mizuki_dim.png");
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/mizuki.png");
	}
}
